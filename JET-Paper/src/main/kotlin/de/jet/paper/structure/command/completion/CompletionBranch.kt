package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.collection.mapToLowercase
import de.jet.jvm.tool.smart.identification.UUID
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType
import de.jet.paper.extension.debugLog
import de.jet.paper.structure.command.completion.CompletionBranch.BranchStatus.*
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.tracing.CompletionTraceResult
import de.jet.paper.structure.command.completion.tracing.PossibleTraceWay

class CompletionBranch(
	override var identity: String = UUID.randomString(),
	override var address: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = Address.address(
		"/"
	),
	override var subBranches: List<CompletionBranch> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	override var content: List<CompletionComponent> = emptyList(),
	val parent: CompletionBranch? = null,
	private var isBranched: Boolean = false,
) : TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>(
	identity,
	address,
	TreeBranchType.OBJECT,
	subBranches,
	content
) {

	var configuration = configuration
		private set

	fun branchesBackToOriginParent(): List<CompletionBranch> {
		val parents = mutableListOf<CompletionBranch>()

		fun inner(branch: CompletionBranch) {
			val innerParent = branch.parent
			if (innerParent != null) {
				parents.add(innerParent)
				innerParent.branchesBackToOriginParent()
			}
		}

		inner(this)

		return parents
	}

	fun buildSyntax() = buildString {
		fun printSubBranches(level: Int = 0, subBranches: List<CompletionBranch>) {
			subBranches.forEach { subBranch ->
				val branchConfig = subBranch.configuration
				appendLine(buildString {
					repeat(level) { append("  ") }
					if (level > 0) append("|- ") else append("/")
					append(buildString {
						if (level > 0) append("(")

						append(subBranch.content.joinToString("|") { it.label }.let { display ->
							display.ifBlank { subBranch.identity }
						})

						if (level > 0) {
							append(")")
							if (!branchConfig.isRequired) append("?")
							if (!branchConfig.ignoreCase) append("^")
							if (branchConfig.mustMatchOutput) append("=")
							if (branchConfig.infiniteSubParameters) append("*")
						}
					})
				})
				if (subBranch.subBranches.isNotEmpty())
					printSubBranches(level + 1, subBranch.subBranches)
			}
		}

		printSubBranches(subBranches = listOf(this@CompletionBranch))

	}

	fun content(vararg completionComponents: CompletionComponent) =
		content(content = completionComponents.toList())

	private fun isInputAllowedByTypes(input: String) =
		content.flatMap { if (it is CompletionComponent.Asset) it.asset.supportedInputType else emptyList() }
			.let { internal ->
				if (internal.isNotEmpty()) {
					return@let internal.any { it.check?.let { it1 -> it1(input) } ?: true }
				} else
					true
			}

	fun computeLocalCompletion() = content.flatMap { it.completion() }

	fun validInput(input: String) =
		(!configuration.mustMatchOutput || this.computeLocalCompletion().also { println("1::$it") }.any { it.equals(input, configuration.ignoreCase) }).also { println("1 -> $it") }
				&& configuration.supportedInputTypes.none { !(it.check?.let { it1 -> it1(input) } ?: true) }.also { println("2 -> $it") }
				&& isInputAllowedByTypes(input).also { println("3 -> $it") }
				&& (!configuration.isRequired || input.isNotBlank()).also { println("4 -> $it") }



	enum class BranchStatus {
		MATCHING,
		OVERFLOW,
		INCOMPLETE,
		FAILED;
	}

	fun trace(inputQuery: List<String>): CompletionTraceResult {
		val waysMatching = mutableListOf<PossibleTraceWay>()
		val waysOverflow = mutableListOf<PossibleTraceWay>()
		val waysIncomplete = mutableListOf<PossibleTraceWay>()
		val waysFailed = mutableListOf<PossibleTraceWay>()

		fun demo(branch: CompletionBranch, depth: Int) {
			debugLog("tracing branch ${branch.identity}[${branch.address}]")
			debugLog("=== INPUT: '$inputQuery'")

			val subBranches = branch.subBranches.also { println("size: ${it.size}") }
			val isValid = branch.validInput((inputQuery.getOrNull(depth) ?: "").also { println("validContent: $it") })
				.also { println("valid: $it") }

			if (isValid && (inputQuery.lastIndex <= depth).also { println("lastIndex <= depth: $it") }) {

				println("[!] branch ${branch.address} matching")
				waysMatching.add(PossibleTraceWay(branch.address, branch.computeLocalCompletion(), inputQuery))

			} else {
				if ((inputQuery.lastIndex < depth).also { println("lastIndex (${inputQuery.lastIndex}) < depth ($depth): $it") }) {
					println("[!] branch ${branch.address} incomplete")
					waysIncomplete.add(PossibleTraceWay(branch.address, branch.computeLocalCompletion(), inputQuery))
				} else {
					println("[!] branch ${branch.address} failed")
					waysFailed.add(
						PossibleTraceWay(
							branch.address,
							branch.computeLocalCompletion(),
							inputQuery.take(depth + 1)
						)
					)
				}
			}

			if (subBranches.isNotEmpty().also { println("subBranches.isNotEmpty: $it") }) {
				subBranches.forEach {
					demo(it, depth + 1)
				}
			}

		}

		fun innerTrace(currentBranch: CompletionBranch, currentDepth: Int, parentBranchStatus: BranchStatus) {
			debugLog("tracing branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus")
			val currentLevelInput = (inputQuery.getOrNull(currentDepth) ?: "").also { println("currentLevelInput: $it") }
			val currentSubBranches = currentBranch.subBranches.also { println("SB-size: ${it.size}") }
			val currentInputValid = currentBranch.validInput(currentLevelInput).also { println("valid: $it -> $currentLevelInput -> $inputQuery") }
			val currentResult: BranchStatus

			// CONTENT START

			when (parentBranchStatus) {
				FAILED -> currentResult = FAILED
				INCOMPLETE -> currentResult = INCOMPLETE
				OVERFLOW, MATCHING -> {
					if (currentInputValid.also { println("valid: $it") }) {
						if (currentInputValid) {
							if (currentBranch.parent?.isRoot == true || (waysMatching + waysOverflow).any { t -> t.address == currentBranch.parent?.address }) {
								if (currentBranch.subBranches.none { it.configuration.isRequired }) {
									if (currentDepth >= inputQuery.lastIndex) {
										currentResult = MATCHING.also { println("case 1") }
									} else {
										currentResult = OVERFLOW.also { println("case 1.2") }
									}
								} else
									currentResult = INCOMPLETE.also { println("case 2") }
							} else {
								if (waysIncomplete.any { t -> t.address == currentBranch.parent?.address }) {
									currentResult = INCOMPLETE.also { println("case 3") }
								} else
									currentResult = FAILED.also { println("case 4") }
							}
						} else
							currentResult = FAILED.also { println("case 5") }
					} else {
						currentResult = FAILED.also { println("case 6") }
					}
				}
			}

			// CONTENT END

			debugLog("branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus is $currentResult")

			when (currentResult) {
				FAILED -> waysFailed.add(PossibleTraceWay(currentBranch.address, currentBranch.computeLocalCompletion(), inputQuery))
				OVERFLOW -> waysOverflow.add(PossibleTraceWay(currentBranch.address, currentBranch.computeLocalCompletion(), inputQuery))
				INCOMPLETE -> waysIncomplete.add(PossibleTraceWay(currentBranch.address, currentBranch.computeLocalCompletion(), inputQuery))
				MATCHING -> waysMatching.add(PossibleTraceWay(currentBranch.address, currentBranch.computeLocalCompletion(), inputQuery))
			}

			currentSubBranches.forEach {
				innerTrace(it, currentDepth + 1, currentResult)
			}

		}

		subBranches.forEach {
			innerTrace(it, 0, MATCHING)
		}

		return CompletionTraceResult(
			waysMatching = waysMatching,
			waysOverflow = waysOverflow,
			waysIncomplete = waysIncomplete,
			waysFailed = waysFailed,
			traceBase = this,
			executedQuery = inputQuery
		)
	}

	fun validateInput(parameters: List<String>): Boolean {
		var currentBranches = listOf(this)
		var query = parameters.firstOrNull() ?: ""
		var depth = 0
		for (x in 1..parameters.size) {
			val nextBranches = currentBranches.flatMap { it.subBranches }
			query = parameters[x - 1]
			if (nextBranches.isEmpty()) break
			currentBranches = nextBranches.filter {
				(!it.configuration.mustMatchOutput || it.computeLocalCompletion()
					.any { c -> c.equals(query, it.configuration.ignoreCase) }) // check input/output match
						&& it.isInputAllowedByTypes(query) // branch type check
						&& it.subBranches.any { !it.configuration.isRequired } // this branch can be executed, without further input
			}
			/*currentBranches = nextBranches.filter {
				query.isBlank()
						|| it.computeLocalCompletion().mapToLowercase().any { it.contains(query, true) }
						&& (!it.configuration.mustMatchOutput && it.isInputAllowedByTypes(query))
			}*/

			depth++
		}

		return true || if (parameters.size > depth && currentBranches.none { it.configuration.infiniteSubParameters }) {
			false
		} else
			currentBranches.none { it.subBranches.any { it.configuration.isRequired } }
	}

	fun computeCompletion(parameters: List<String>): List<String> {

		var currentBranches = listOf(this)
		var query = parameters.firstOrNull() ?: ""
		var depth = 0

		for (x in 1..parameters.size) {
			val nextBranches = currentBranches.flatMap { it.subBranches }
			query = parameters[x - 1]
			if (nextBranches.isEmpty()) break
			currentBranches = nextBranches.filter {
				query.isBlank()
						|| it.computeLocalCompletion().mapToLowercase().any { it.contains(query, true) }
						|| (!it.configuration.mustMatchOutput && it.isInputAllowedByTypes(query))
			}
			depth++
		}

		return if (parameters.size > depth) {
			listOf(" ")
		} else
			currentBranches.flatMap { it.computeLocalCompletion() }.partition { it.startsWith(query, true) }.let {
				it.first + it.second.filter { it.contains(query, true) }
			}
	}

	/**
	 * @throws IllegalStateException if the branch already has sub-branches
	 * @throws IllegalStateException if the parent-branch is non-required, but this branch is configured as required
	 */
	fun configure(process: CompletionBranchConfiguration.() -> Unit) {
		if (!isBranched) {
			val newConfiguration = configuration.apply(process)

			if (parent != null && !parent.configuration.isRequired && newConfiguration.isRequired)
				throw IllegalStateException("Cannot set required on a non-required branch path (parent is non-required)")

			configuration = newConfiguration
		} else throw IllegalStateException("Cannot configure a branched, that already has sub-branches")
	}

	/**
	 * @throws IllegalStateException if the new branch follows an infinite-parameter branch
	 */
	fun branch(
		identity: String = UUID.randomString(),
		path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = this.address / identity,
		configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
		process: CompletionBranch.() -> Unit,
	) {

		if (parent != null && parent.configuration.infiniteSubParameters)
			throw IllegalStateException("Cannot branch a branch with infinite sub-parameters")

		isBranched = true
		subBranches += CompletionBranch(
			identity = identity,
			address = path,
			subBranches = emptyList(),
			configuration = configuration,
			content = emptyList(),
			parent = this,
		).apply(process)
	}

	fun addContent(vararg components: CompletionComponent) {
		content += components
	}

}

fun buildCompletion(
	path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = Address.address("/"),
	subBranches: List<CompletionBranch> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	content: List<CompletionComponent> = emptyList(),
	process: CompletionBranch.() -> Unit,
) = CompletionBranch("root", path, subBranches, configuration, content).apply(process)

fun emptyCompletion() =
	buildCompletion(process = { })