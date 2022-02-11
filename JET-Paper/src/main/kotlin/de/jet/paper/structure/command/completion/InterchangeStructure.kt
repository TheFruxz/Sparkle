package de.jet.paper.structure.command.completion

import de.jet.jvm.tool.smart.identification.UUID
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType
import de.jet.paper.extension.debugLog
import de.jet.paper.structure.command.completion.InterchangeStructure.BranchStatus.*
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.tracing.CompletionTraceResult
import de.jet.paper.structure.command.completion.tracing.CompletionTraceResult.Conclusion.EMPTY
import de.jet.paper.structure.command.completion.tracing.PossibleTraceWay

class InterchangeStructure(
	override var identity: String = UUID.randomString(),
	override var address: Address<InterchangeStructure> = Address.address("/"),
	override var subBranches: List<InterchangeStructure> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	override var content: List<CompletionComponent> = emptyList(),
	val parent: InterchangeStructure? = null,
	private var isBranched: Boolean = false,
) : TreeBranch<InterchangeStructure, List<CompletionComponent>, TreeBranchType>(
	identity,
	address,
	TreeBranchType.OBJECT,
	subBranches,
	content
) {

	var configuration = configuration
		private set

	fun branchesBackToOriginParent(): List<InterchangeStructure> {
		val parents = mutableListOf<InterchangeStructure>()

		fun inner(branch: InterchangeStructure) {
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
		fun construct(level: Int = 0, subBranches: List<InterchangeStructure>) {
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
					construct(level + 1, subBranch.subBranches)
			}
		}

		construct(subBranches = listOf(this@InterchangeStructure))

	}

	fun content(vararg completionComponents: CompletionComponent) =
		content(content = completionComponents.toList())

	private fun isInputAllowedByTypes(input: String) =
		content.flatMap { if (it is CompletionComponent.Asset) it.asset.supportedInputType else emptyList() }
			.let { internal ->
				if (internal.isNotEmpty()) {
					return@let internal.any { it.isValid(input) }
				} else
					true
			}

	fun computeLocalCompletion() = content.flatMap { it.completion() }

	fun validInput(input: String) =
		(!configuration.mustMatchOutput || this.computeLocalCompletion()
			.any { it.equals(input, configuration.ignoreCase) })
				&& configuration.supportedInputTypes.none { !it.isValid(input) }
				&& isInputAllowedByTypes(input)
				&& (!configuration.isRequired || input.isNotBlank())


	enum class BranchStatus {
		MATCHING,
		OVERFLOW,
		INCOMPLETE,
		NO_DESTINATION,
		FAILED;
	}

	fun trace(inputQuery: List<String>): CompletionTraceResult<InterchangeStructure> {
		val waysMatching = mutableListOf<PossibleTraceWay<InterchangeStructure>>()
		val waysOverflow = mutableListOf<PossibleTraceWay<InterchangeStructure>>()
		val waysIncomplete = mutableListOf<PossibleTraceWay<InterchangeStructure>>()
		val waysFailed = mutableListOf<PossibleTraceWay<InterchangeStructure>>()
		val waysNoDestination = mutableListOf<PossibleTraceWay<InterchangeStructure>>()

		fun innerTrace(currentBranch: InterchangeStructure, currentDepth: Int, parentBranchStatus: BranchStatus) {
			debugLog("tracing branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus")
			val currentLevelInput = inputQuery.getOrNull(currentDepth) ?: ""
			val currentSubBranches = currentBranch.subBranches
			val currentInputValid = currentBranch.validInput(currentLevelInput)
			val currentResult: BranchStatus

			// CONTENT START

			when (parentBranchStatus) {
				FAILED -> currentResult = FAILED
				INCOMPLETE -> currentResult = INCOMPLETE
				NO_DESTINATION, OVERFLOW, MATCHING -> {

					if (currentInputValid) {
						if (currentBranch.parent?.isRoot == true || (waysMatching + waysOverflow + waysNoDestination).any { t -> t.address == currentBranch.parent?.address }) {
							if (currentBranch.subBranches.any { it.configuration.isRequired } && inputQuery.lastIndex < currentDepth) {
								currentResult = INCOMPLETE
							} else if (currentDepth >= inputQuery.lastIndex || currentBranch.configuration.infiniteSubParameters) {
								if (currentSubBranches.isNotEmpty() && currentSubBranches.all { it.configuration.isRequired }) {
									currentResult = NO_DESTINATION // This branch has to be completed with its sub-branches
								} else
									currentResult = MATCHING
							} else {
								currentResult = OVERFLOW
							}

						} else {
							if (waysIncomplete.any { t -> t.address == currentBranch.parent?.address }) {
								currentResult = INCOMPLETE
							} else {
								currentResult = FAILED
							}
						}

					} else {
						if (((waysMatching + waysOverflow + waysNoDestination).any { it.address == currentBranch.parent?.address } && currentLevelInput.isBlank()) || (currentBranch.parent?.isRoot == true && inputQuery.isEmpty())) {
							currentResult = INCOMPLETE
						} else {
							currentResult = FAILED
						}
					}
				}
			}

			// CONTENT END

			debugLog("branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus is $currentResult")

			val outputBuild = PossibleTraceWay<InterchangeStructure>(
				address = currentBranch.address,
				branch = currentBranch,
				cachedCompletion = currentBranch.computeLocalCompletion(),
				tracingDepth = currentDepth,
				usedQueryState = inputQuery,
			)

			when (currentResult) {
				FAILED -> waysFailed.add(outputBuild)
				OVERFLOW -> waysOverflow.add(outputBuild)
				INCOMPLETE -> waysIncomplete.add(outputBuild)
				MATCHING -> waysMatching.add(outputBuild)
				NO_DESTINATION -> waysNoDestination.add(outputBuild)
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
			waysNoDestination = waysNoDestination,
			traceBase = this,
			executedQuery = inputQuery
		)
	}

	fun validateInput(parameters: List<String>): Boolean {
		val trace = trace(parameters)

		return if (trace.conclusion == EMPTY && parameters.isEmpty()) {
			true
		} else
			trace.waysMatching.isNotEmpty()
	}

	fun computeCompletion(parameters: List<String>): List<String> {

		val query = (parameters.lastOrNull() ?: "")
		val traceBase = parameters.dropLast(1)
		val tracing = trace(traceBase)
		val tracingContent = tracing.let { return@let (it.waysIncomplete + it.waysMatching + it.waysNoDestination) }
		val filteredContent = tracingContent.filter { it.tracingDepth == parameters.lastIndex }
		val flattenedContentCompletion = filteredContent.flatMap { it.cachedCompletion }
		val distinctedCompletion = flattenedContentCompletion.distinct()
		val partitionedCompletion = distinctedCompletion.partition { it.startsWith(query, true) }
		val mergedCompletion = partitionedCompletion.let {
			return@let it.first + it.second.filter { it.contains(query, true) }
		}

		return mergedCompletion
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
		path: Address<InterchangeStructure> = this.address / identity,
		configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
		process: InterchangeStructure.() -> Unit,
	) {

		if (parent != null && parent.configuration.infiniteSubParameters)
			throw IllegalStateException("Cannot branch a branch with infinite sub-parameters")

		isBranched = true
		subBranches += InterchangeStructure(
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
	path: Address<InterchangeStructure> = Address.address("/"),
	subBranches: List<InterchangeStructure> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	content: List<CompletionComponent> = emptyList(),
	process: InterchangeStructure.() -> Unit,
) = InterchangeStructure("root", path, subBranches, configuration, content).apply(process)

fun emptyCompletion() =
	buildCompletion(process = { })