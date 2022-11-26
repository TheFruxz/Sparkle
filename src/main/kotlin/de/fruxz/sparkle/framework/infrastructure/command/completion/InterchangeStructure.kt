package de.fruxz.sparkle.framework.infrastructure.command.completion

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.logging.getLogger
import de.fruxz.ascend.extension.math.maxTo
import de.fruxz.ascend.tool.smart.positioning.Address
import de.fruxz.ascend.tree.TreeBranch
import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.time.hasCooldown
import de.fruxz.sparkle.framework.extension.time.setCooldown
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.*
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure.TraceResult.TraceStatus
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset.CompletionContext
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.tracing.CompletionTraceResult.Conclusion.*
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.permission.hasApproval
import org.bukkit.entity.Player
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class InterchangeStructure<EXECUTOR : InterchangeExecutor>(
	override var identity: String = "${UUID.randomUUID()}",
	override var address: Address<InterchangeStructure<EXECUTOR>> = Address.address("/"),
	override var subBranches: List<InterchangeStructure<EXECUTOR>> = emptyList(),
	override var content: List<CompletionComponent> = emptyList(),
	val parent: InterchangeStructure<EXECUTOR>? = null,
	var label: String = "",
	var cooldown: Duration = Duration.ZERO,
	private var isBranched: Boolean = false,
	var userRestriction: InterchangeUserRestriction = NOT_RESTRICTED,
	var requiredApprovals: List<Approval> = emptyList(),
	var configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	var onExecution: (suspend InterchangeAccess<EXECUTOR>.() -> InterchangeResult)? = null,
) : TreeBranch<InterchangeStructure<EXECUTOR>, List<CompletionComponent>>(
	identity = identity,
	address = address,
	subBranches = subBranches,
	content = content,
) {

	private val log = getLogger("InterchangeStructure")

	fun buildSyntax(executor: InterchangeExecutor?) = buildString {
		fun construct(level: Int = 0, internalExecutor: InterchangeExecutor?, subBranches: List<InterchangeStructure<EXECUTOR>>) {

			subBranches
				.filter {
					it.requiredApprovals.all { approval -> executor?.hasApproval(approval) != false }
							&& executor?.let { it1 -> it.userRestriction.match(it1) } != false // remove wrong user types
				}
				.forEach { subBranch ->
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
		if (onExecution != null) log.warning("Overwriting existing execution process!")

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
		if (onExecution != null) log.warning("Overwriting existing execution process!")

		onExecution = {
			process()
			result
		}
	}

	private fun computeLocalCompletion(context: CompletionContext) = content.flatMap { it.completion(context) }

	private fun validInput(executor: InterchangeExecutor, input: String, inputQuery: List<String>) =
		(!configuration.mustMatchOutput || this.computeLocalCompletion(
			CompletionContext(
				executor,
				inputQuery,
				input,
				this.configuration.ignoreCase,
		)).any { it.equals(input, configuration.ignoreCase) }) && (!configuration.isRequired || input.isNotBlank())

	fun trace(executor: InterchangeExecutor, rawInput: List<String>): TraceResult<EXECUTOR, InterchangeStructure<EXECUTOR>> {
		val processedInput = rawInput // TODO here the input should be split, if configuration allows multi-word input

		return TraceResult(
			base = this,
			query = processedInput,
			traced = buildMap<TraceStatus, MutableSet<TraceWay<InterchangeStructure<EXECUTOR>>>> {

				// Place every default value
				putAll(TraceStatus.values().associateWith { mutableSetOf() })

				// Provide recursive trace function

				fun inner(currentBranch: InterchangeStructure<EXECUTOR>, currentDepth: Int, parentStatus: TraceStatus) {
					if (currentDepth > MAX_TRACE_DEPTH) {
						debugLog("Trace depth of $MAX_TRACE_DEPTH exceeded!")
						return
					}

					debugLog("tracing branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentStatus")

					val localInput = processedInput.getOrNull(currentDepth) ?: "" // TODO check if this is correct -> edit: should be correct, because start is 0
					val localInputAccepted = currentBranch.validInput(executor, localInput, processedInput) // TODO check if processed input can be used by validInput function (compatible with multi-words?)
					val isLocalExecutableRoot = currentBranch.isRoot && processedInput.isEmpty() && currentBranch.onExecution != null

					// Starting trace

					val localStatus: TraceStatus = when (parentStatus) {
						TraceStatus.FAILED, TraceStatus.INCOMPLETE -> parentStatus
						TraceStatus.NO_DESTINATION, TraceStatus.OVERFLOW, TraceStatus.MATCHING -> {

							when {
								!currentBranch.userRestriction.match(executor) -> TraceStatus.FAILED // user restriction not met
								!currentBranch.requiredApprovals.all { executor.hasApproval(it) } -> TraceStatus.FAILED // not enough of the required approvals!
								!localInputAccepted && isLocalExecutableRoot -> TraceStatus.MATCHING // root is executable and input is not accepted/existing
								!localInputAccepted && !isLocalExecutableRoot -> {
									when {
										(get(TraceStatus.MATCHING)!! + get(TraceStatus.OVERFLOW)!! + get(TraceStatus.NO_DESTINATION)!!).any { it.address == currentBranch.parent?.address } && localInput.isBlank() -> TraceStatus.INCOMPLETE
										currentBranch.parent?.isRoot == true && processedInput.isEmpty() -> TraceStatus.INCOMPLETE // TODO check if this is used at all
										else -> TraceStatus.FAILED
									}
								}
								currentBranch.subBranches.any { it.configuration.isRequired } && processedInput.lastIndex < currentDepth -> TraceStatus.INCOMPLETE
								currentDepth < processedInput.lastIndex && !currentBranch.configuration.infiniteSubParameters -> TraceStatus.OVERFLOW
								currentBranch.subBranches.isNotEmpty() && currentBranch.subBranches.all { it.configuration.isRequired } && currentBranch.onExecution == null -> TraceStatus.NO_DESTINATION
								else -> TraceStatus.MATCHING
							}

						}
					}

					// End trace -> process result

					debugLog("branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentStatus is $localStatus")

					this[localStatus]!!.add(
						TraceWay(
							address = currentBranch.address,
							branch = currentBranch,
							cachedCompletion = currentBranch.computeLocalCompletion( // TODO check, if not computeCompletion(...) is better!
								context = CompletionContext(
									executor = executor,
									fullLineInput = processedInput,
									input = localInput,
									ignoreCase = currentBranch.configuration.ignoreCase,
								)
							),
							depth = currentDepth,
							usedQuery = processedInput,
						)
					)

					// process result -> continue trace with sub-branches

					if (!currentBranch.isRoot) {
						currentBranch.subBranches.forEach {
							inner(it.forceCast(), currentDepth + 1, localStatus)
						}
					}

				}

				// Start tracing

				(subBranches + this@InterchangeStructure).forEach {
					inner(it.forceCast(), 0, TraceStatus.MATCHING) // TODO this also pretends, that the root is matching, even if it does not!!!
				}

			}
		)

	}

	data class TraceResult<EXECUTOR : InterchangeExecutor, BRANCH : TreeBranch<*, *>>(
		val traced: Map<TraceStatus, Set<TraceWay<BRANCH>>>, // the ways, found by the trace
		val base: InterchangeStructure<EXECUTOR>, // the base of the trace
		val query: List<String>, // the original query, that was used to execute the trace
	) {

		val conclusion = when {
			(traced[TraceStatus.MATCHING]!! + traced[TraceStatus.NO_DESTINATION]!!).size == 1 -> {
				RESULT
			}
			traced[TraceStatus.MATCHING]!!.isEmpty() -> {
				when {
					traced[TraceStatus.INCOMPLETE]!!.isEmpty() && traced[TraceStatus.OVERFLOW]!!.isEmpty() && traced[TraceStatus.FAILED]!!.isEmpty() && traced[TraceStatus.NO_DESTINATION]!!.isEmpty() -> {
						EMPTY
					}
					else -> NO_RESULT
				}
			}
			else -> MULTIPLE_RESULTS
		}

		enum class TraceStatus {
			FAILED,
			OVERFLOW,
			INCOMPLETE,
			MATCHING,
			NO_DESTINATION;
		}

	}

	data class TraceWay<T : TreeBranch<*, *>>(
		val branch: T,
		val address: Address<T>, // todo check, if this definitely needed, because address is computed from branch
		val cachedCompletion: List<String>,
		val depth: Int,
		val usedQuery: List<String>,
	)

	fun validateInput(parameters: List<String>, executor: InterchangeExecutor): Boolean =
		trace(executor, parameters).conclusion == RESULT

	suspend fun performExecution(access: InterchangeAccess<out InterchangeExecutor>): InterchangeResult {
		@OptIn(ExperimentalTime::class)
		return measureTimedValue { trace(access.executor, access.parameters) }.let { (trace, duration) ->
			when (trace.traced[TraceStatus.MATCHING]!!.size.maxTo(2)) {
				0, 2 -> WRONG_USAGE
				else -> {
					val extrapolatedTrace = trace.traced[TraceStatus.MATCHING]!!.first()
					val extrapolatedBranch = extrapolatedTrace.branch

					if (!extrapolatedBranch.cooldown.isPositive() || access.executor !is Player || !access.executor.hasCooldown(access.interchange.key() to extrapolatedBranch.address)) {

						// add cooldown to executor, if executor is player and cooldown is positive
						if (extrapolatedBranch.cooldown.isPositive()) access.executor.asPlayerOrNull?.setCooldown(access.interchange.key() to extrapolatedBranch.address, extrapolatedBranch.cooldown)

						// execute the execution block
						extrapolatedBranch.onExecution?.invoke(access.copy(additionalParameters = access.parameters.drop(extrapolatedTrace.depth + 1)).forceCast()) ?: WRONG_USAGE

					} else {
						with(access.interchange.cooldownReaction) {
							access.reaction()
						}
						BRANCH_COOLDOWN
					}

				}
			}
		}
	}

	fun computeCompletion(parameters: List<String>, executor: InterchangeExecutor): List<String> {

		val query = (parameters.lastOrNull() ?: "")
		val traceBase = parameters.dropLast(1) // remove latest, to remove the 'uncompleted' string
		val tracing = trace(executor, traceBase)
		val tracingContent = tracing.let { return@let (it.traced[TraceStatus.INCOMPLETE]!! + it.traced[TraceStatus.MATCHING]!! + it.traced[TraceStatus.NO_DESTINATION]!!) }
		val filteredContent = tracingContent.filter { it.depth == parameters.lastIndex }
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
		userRestriction: InterchangeUserRestriction = this.userRestriction,
		identity: String = (parent?.identity ?: "") + "/way-${parent?.subBranches?.size ?: 0}",
		path: Address<InterchangeStructure<EXECUTOR>> = this.address / identity,
		configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
		process: InterchangeStructure<EXECUTOR>.() -> Unit,
	) {

		if (parent != null && parent.configuration.infiniteSubParameters)
			throw IllegalStateException("Cannot branch a branch with infinite sub-parameters")

		if (parent != null) {
			when {
				parent.userRestriction != NOT_RESTRICTED && userRestriction == NOT_RESTRICTED -> throw IllegalStateException("Cannot branch a restricted branch with a non-restricted branch")
				parent.userRestriction == ONLY_PLAYERS && userRestriction != ONLY_PLAYERS -> throw IllegalStateException("Cannot branch a player-only branch with a non-player branch")
				parent.userRestriction == ONLY_CONSOLE && userRestriction != ONLY_CONSOLE -> throw IllegalStateException("Cannot branch a console-only branch with a non-console branch")
			}
		}

		isBranched = true
		subBranches += InterchangeStructure(
			identity = identity,
			address = path,
			subBranches = emptyList(),
			configuration = configuration,
			content = emptyList(),
			parent = this,
			requiredApprovals = requiredApprovals,
			userRestriction = userRestriction,
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

	fun restrict(restriction: InterchangeUserRestriction) {
		this.userRestriction = restriction
	}

	companion object {

		/**
		 * This constant value defines the maximal depth of the tracing algorithm.
		 * If the tracing algorithm reaches this depth, it will stop and return the current state.
		 * @author Fruxz
		 * @since 1.0
		 */
		const val MAX_TRACE_DEPTH = 100

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