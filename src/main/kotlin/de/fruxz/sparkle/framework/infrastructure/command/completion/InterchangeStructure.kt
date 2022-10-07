package de.fruxz.sparkle.framework.infrastructure.command.completion

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.math.maxTo
import de.fruxz.ascend.tool.smart.positioning.Address
import de.fruxz.ascend.tree.TreeBranch
import de.fruxz.ascend.tree.TreeBranchType
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure.BranchStatus.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset.CompletionContext
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.tracing.CompletionTraceResult
import de.fruxz.sparkle.framework.infrastructure.command.completion.tracing.CompletionTraceResult.Conclusion.EMPTY
import de.fruxz.sparkle.framework.infrastructure.command.completion.tracing.PossibleTraceWay
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.asPlayer
import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.time.getCooldown
import de.fruxz.sparkle.framework.extension.time.hasCooldown
import de.fruxz.sparkle.framework.extension.time.setCooldown
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.permission.hasApproval
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.time.Duration

class InterchangeStructure<EXECUTOR : InterchangeExecutor>(
	override var identity: String = "${UUID.randomUUID()}",
	override var address: Address<InterchangeStructure<EXECUTOR>> = Address.address("/"),
	override var subBranches: List<InterchangeStructure<EXECUTOR>> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	override var content: List<CompletionComponent> = emptyList(),
	val parent: InterchangeStructure<EXECUTOR>? = null,
	private var isBranched: Boolean = false,
	var requiredApprovals: List<Approval> = emptyList(),
	var cooldown: Duration = Duration.ZERO,
	var onExecution: (suspend InterchangeAccess<EXECUTOR>.() -> InterchangeResult)? = null,
	var label: String = "",
) : TreeBranch<InterchangeStructure<EXECUTOR>, List<CompletionComponent>, TreeBranchType>(
	identity = identity,
	address = address,
	branchType = TreeBranchType.OBJECT,
	subBranches = subBranches,
	content = content,
) {

	var configuration = configuration
		private set

	fun buildSyntax(executor: InterchangeExecutor?) = buildString {
		fun construct(level: Int = 0, internalExecutor: InterchangeExecutor?, subBranches: List<InterchangeStructure<EXECUTOR>>) {

			subBranches.filter { it.requiredApprovals.all { approval -> executor?.hasApproval(approval) != false } }.forEach { subBranch ->
				val branchConfig = subBranch.configuration
				appendLine(buildString {
					repeat(level) { append("  ") }
					if (level > 0) append("|- ") else append("/")
					append(buildString {
						if (level > 0) append("(")

						append(
							when {
								label.isNotBlank() -> label
								else -> subBranch.content
									.joinToString("|") { it.label }
									.let { display ->
										display.ifBlank { subBranch.identity }
									}
							}
						)

						if (level > 0) {
							append(")")
							if (subBranch.isRoot && subBranch.onExecution != null) append("<")
							if (!branchConfig.isRequired) append("?")
							if (!branchConfig.ignoreCase) append("^")
							if (branchConfig.mustMatchOutput) append("=")
							if (branchConfig.infiniteSubParameters) append("*")

						}
					})
				})

				val subSubBranches = subBranch.subBranches.filter { it.requiredApprovals.all { approval -> executor?.hasApproval(approval) != false } }

				if (subSubBranches.isNotEmpty())
					construct(level + 1, internalExecutor, subSubBranches.forceCast())

			}
		}

		construct(subBranches = listOf(this@InterchangeStructure), internalExecutor = executor)

	}

	fun content(vararg completionComponents: CompletionComponent) =
		content(content = completionComponents.toList())

	fun execution(process: (suspend InterchangeAccess<EXECUTOR>.() -> InterchangeResult)?) {
		onExecution = process
	}

	fun requiredApproval(vararg approvals: Approval) {
		requiredApprovals = approvals.toList()
	}

	/**
	 * This function represents the [execution] function, but returns
	 * [result] internally as the [InterchangeResult] instead of the
	 * returned [InterchangeResult] from the [process].
	 * @param process the code, that the interchange executor triggers
	 * @author Fruxz
	 * @since 1.0
	 */
	@JvmName("executionWithoutReturn")
	fun concludedExecution(result: InterchangeResult = SUCCESS, process: suspend InterchangeAccess<EXECUTOR>.() -> Unit) {
		onExecution = {
			process()
			result
		}
	}

	private fun isInputAllowedByTypes(input: String) =
		content.flatMap { if (it is CompletionComponent.Asset) it.asset.supportedInputType else emptyList() }
			.let { internal ->
				if (content.filterIsInstance<CompletionComponent.Static>().isNotEmpty()) {
					true
				} else if (internal.isNotEmpty()) {
					return@let internal.any { it.isValid(input) }
				} else
					true
			}

	private fun computeLocalCompletion(context: CompletionContext) = content.flatMap { it.completion(context) }

	private fun validInput(executor: InterchangeExecutor, input: String, inputQuery: List<String>) =
		(!configuration.mustMatchOutput || this.computeLocalCompletion(
			CompletionContext(
			executor,
			inputQuery,
			input,
			this.configuration.ignoreCase,
		))
			.any { it.equals(input, configuration.ignoreCase) })
				&& configuration.supportedInputTypes.any { it.isValid(input) }
				&& isInputAllowedByTypes(input)
				&& (!configuration.isRequired || input.isNotBlank())


	enum class BranchStatus {
		MATCHING,
		OVERFLOW,
		INCOMPLETE,
		NO_DESTINATION,
		FAILED;
	}

	fun trace(inputQuery: List<String>, executor: InterchangeExecutor, availableExecutionRepresentsSolution: Boolean = true): CompletionTraceResult<EXECUTOR, InterchangeStructure<EXECUTOR>> {
		val waysMatching = mutableListOf<PossibleTraceWay<InterchangeStructure<EXECUTOR>>>()
		val waysOverflow = mutableListOf<PossibleTraceWay<InterchangeStructure<EXECUTOR>>>()
		val waysIncomplete = mutableListOf<PossibleTraceWay<InterchangeStructure<EXECUTOR>>>()
		val waysFailed = mutableListOf<PossibleTraceWay<InterchangeStructure<EXECUTOR>>>()
		val waysNoDestination = mutableListOf<PossibleTraceWay<InterchangeStructure<EXECUTOR>>>()

		fun innerTrace(currentBranch: InterchangeStructure<EXECUTOR>, currentDepth: Int, parentBranchStatus: BranchStatus) {
			debugLog("tracing branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus")
			val currentLevelInput = inputQuery.getOrNull(currentDepth) ?: ""
			val currentSubBranches = currentBranch.subBranches
			val currentInputValid = currentBranch.validInput(executor, currentLevelInput, inputQuery)
			val currentResult: BranchStatus
			val isAccessTargetValidRoot = currentBranch.isRoot && inputQuery.isEmpty() && availableExecutionRepresentsSolution && currentBranch.onExecution != null

			// CONTENT START

			when (parentBranchStatus) {
				FAILED -> currentResult = FAILED
				INCOMPLETE -> currentResult = INCOMPLETE
				NO_DESTINATION, OVERFLOW, MATCHING -> {

					if (currentBranch.requiredApprovals.all { executor.hasApproval(it) }) { // check if executor has all required approvals
						if (currentInputValid) {
							if (currentBranch.parent?.isRoot == true || (waysMatching + waysOverflow + waysNoDestination).any { t -> t.address == currentBranch.parent?.address }) {
								currentResult = if (currentBranch.subBranches.any { it.configuration.isRequired } && inputQuery.lastIndex < currentDepth) {
									INCOMPLETE
								} else if (currentDepth >= inputQuery.lastIndex || currentBranch.configuration.infiniteSubParameters) {
									if ((currentSubBranches.isNotEmpty() && currentSubBranches.all { it.configuration.isRequired }) && !(availableExecutionRepresentsSolution && currentBranch.onExecution != null)) {
										NO_DESTINATION // This branch has to be completed with its sub-branches
									} else {
										MATCHING
									}
								} else {
									OVERFLOW
								}

							} else {
								currentResult = if (waysIncomplete.any { t -> t.address == currentBranch.parent?.address }) {
									INCOMPLETE
								} else {
									FAILED
								}
							}

						} else if (isAccessTargetValidRoot) {
							currentResult = MATCHING
						} else {
							currentResult = if (((waysMatching + waysOverflow + waysNoDestination).any { it.address == currentBranch.parent?.address } && currentLevelInput.isBlank()) || (currentBranch.parent?.isRoot == true && inputQuery.isEmpty())) {
								INCOMPLETE
							} else {
								FAILED
							}
						}
					} else
						currentResult = FAILED // not enough approvals!
				}
			}

			// CONTENT END

			debugLog("branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentBranchStatus is $currentResult")

			val outputBuild = PossibleTraceWay(
				address = currentBranch.address,
				branch = currentBranch,
				cachedCompletion = currentBranch.computeLocalCompletion(
					CompletionContext(
						executor,
						inputQuery,
						currentLevelInput,
						currentBranch.configuration.ignoreCase
				)
				),
				tracingDepth = currentDepth,
				usedQueryState = inputQuery,
			)

			when (currentResult) {
				FAILED -> waysFailed.add(outputBuild.forceCast())
				OVERFLOW -> waysOverflow.add(outputBuild.forceCast())
				INCOMPLETE -> waysIncomplete.add(outputBuild.forceCast())
				MATCHING -> waysMatching.add(outputBuild.forceCast())
				NO_DESTINATION -> waysNoDestination.add(outputBuild.forceCast())
			}

			if (!currentBranch.isRoot) {
				currentSubBranches.forEach {
					innerTrace(it.forceCast(), currentDepth + 1, currentResult)
				}
			}

		}

		(subBranches + this).forEach {
			innerTrace(it.forceCast(), 0, MATCHING)
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

	fun validateInput(parameters: List<String>, executor: InterchangeExecutor): Boolean {
		val trace = trace(parameters, executor)

		return if (trace.conclusion == EMPTY && parameters.isEmpty()) {
			true
		} else
			trace.waysMatching.isNotEmpty()
	}

	suspend fun performExecution(access: InterchangeAccess<out InterchangeExecutor>): InterchangeResult {
		return trace(access.parameters, access.executor).let { trace ->
			when (trace.waysMatching.size.maxTo(2)) {
				0, 2 -> WRONG_USAGE
				else -> {
					val extrapolatedTrace = trace.waysMatching.first()
					val extrapolatedBranch = extrapolatedTrace.branch

					if (!extrapolatedBranch.cooldown.isPositive() || access.executor !is Player || !access.executor.hasCooldown(access.interchange.key() to extrapolatedBranch.address)) {

						// add cooldown to executor, if executor is player and cooldown is positive
						if (extrapolatedBranch.cooldown.isPositive()) access.executor.asPlayerOrNull?.setCooldown(access.interchange.key() to extrapolatedBranch.address, extrapolatedBranch.cooldown)

						// execute the execution block
						extrapolatedBranch.onExecution?.invoke(access.copy(additionalParameters = access.parameters.drop(extrapolatedTrace.tracingDepth + 1)).forceCast()) ?: WRONG_USAGE

					} else {
						access.interchange.cooldownFeedback(access.executor, access.executor.asPlayer.getCooldown(access.interchange.key() to extrapolatedBranch.address))
						BRANCH_COOLDOWN
					}

				}
			}
		}
	}

	fun computeCompletion(parameters: List<String>, executor: InterchangeExecutor): List<String> {

		val query = (parameters.lastOrNull() ?: "")
		val traceBase = parameters.dropLast(1)
		val tracing = trace(traceBase, executor)
		val tracingContent = tracing.let { return@let (it.waysIncomplete + it.waysMatching + it.waysNoDestination) }
		val filteredContent = tracingContent.filter { it.tracingDepth == parameters.lastIndex }
		val flattenedContentCompletion = filteredContent.flatMap { it.cachedCompletion }
		val distinctCompletion = flattenedContentCompletion.distinct()
		val partitionedCompletion = distinctCompletion.partition { it.startsWith(query, true) }
		val mergedCompletion = partitionedCompletion.let { partitioned ->
			return@let partitioned.first + partitioned.second.filter { it.contains(query, true) }
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
		identity: String = (parent?.identity ?: "") + "/way-${parent?.subBranches?.size ?: 0}",
		path: Address<InterchangeStructure<EXECUTOR>> = this.address / identity,
		configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
		process: InterchangeStructure<EXECUTOR>.() -> Unit,
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
			requiredApprovals = requiredApprovals,
		).apply(process)
	}

	fun addContent(vararg components: CompletionComponent) {
		content += components
	}

	fun addContent(vararg statics: String) =
		addContent(CompletionComponent.static(statics.toSet()))

	fun addContent(vararg assets: CompletionAsset<*>) =
		addContent(components = assets.map { CompletionComponent.asset(it) }.toTypedArray())

	fun addApprovalRequirement(vararg approval: Approval) {
		requiredApprovals += approval
	}

	infix fun cooldown(cooldown: Duration) {
		this.cooldown = cooldown
	}

	fun label(label: String) {
		this.label = label
	}

}

fun <EXECUTOR : InterchangeExecutor> buildInterchangeStructure(
	path: Address<InterchangeStructure<EXECUTOR>> = Address.address("/"),
	subBranches: List<InterchangeStructure<EXECUTOR>> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	content: List<CompletionComponent> = emptyList(),
	requiredApprovals: List<Approval> = emptyList(),
	builder: InterchangeStructure<EXECUTOR>.() -> Unit,
) = InterchangeStructure(
	identity = "root",
	address = path,
	subBranches = subBranches,
	configuration = configuration,
	content = content,
	requiredApprovals = requiredApprovals
).apply(builder)

fun emptyInterchangeStructure() =
	buildInterchangeStructure<InterchangeExecutor>(builder = { })