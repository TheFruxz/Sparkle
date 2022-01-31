package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.collection.mapToLowercase
import de.jet.jvm.tool.smart.identification.UUID
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType
import de.jet.paper.extension.debugLog
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
		(!configuration.mustMatchOutput || this.computeLocalCompletion().also { println("1::$it") }
			.any { it.equals(input, configuration.ignoreCase) }).also { println("1 -> $it") }
				&& configuration.supportedInputTypes.none { !(it.check?.let { it1 -> it1(input) } ?: true) }
			.also { println("2 -> $it") }
				&& isInputAllowedByTypes(input).also { println("3 -> $it") }
				&& !(input.isBlank() && configuration.isRequired).also { println("4 -> $it") }

	fun trace(inputQuery: List<String>): CompletionTraceResult {
		val waysMatching = mutableListOf<PossibleTraceWay>()
		val waysIncomplete = mutableListOf<PossibleTraceWay>()
		val waysFailed = mutableListOf<PossibleTraceWay>()

		fun demo(branch: CompletionBranch, depth: Int) {
			debugLog("tracing branch ${branch.identity}[${branch.address}]")

			val subBranches = branch.subBranches.also { println("size: ${it.size}") }
			val isValid = branch.validInput(inputQuery.getOrNull(depth) ?: "").also { println("valid: $it") }

			if (isValid && (inputQuery.lastIndex <= depth).also { println("lastIndex <= depth: $it") }) {

				if ((inputQuery.lastIndex < depth).also { println("lastIndex < depth: $it") }) {
					println("[!] branch ${branch.address} incomplete")
					waysIncomplete.add(PossibleTraceWay(branch.address, branch.computeLocalCompletion(), inputQuery))
				} else {
					println("[!] branch ${branch.address} matching")
					waysMatching.add(PossibleTraceWay(branch.address, branch.computeLocalCompletion(), inputQuery))
				}

				if (subBranches.isEmpty().also { println("subBranches.isEmpty: $it") }) {
					// TODO: 31.01.2022 moved from up block to here
				} else {
					subBranches.forEach {
						demo(it, depth + 1)
					}
				}
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

		subBranches.forEach {
			demo(it, 0)
		}

		return CompletionTraceResult(
			waysMatching = waysMatching,
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
	identity: String = UUID.randomString(),
	path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = Address.address("/"),
	subBranches: List<CompletionBranch> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	content: List<CompletionComponent> = emptyList(),
	process: CompletionBranch.() -> Unit,
) = CompletionBranch(identity, path, subBranches, configuration, content).apply(process)

fun emptyCompletion() =
	buildCompletion(process = { })