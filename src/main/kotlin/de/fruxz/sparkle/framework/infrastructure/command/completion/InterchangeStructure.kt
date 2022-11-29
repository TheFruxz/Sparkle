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
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure.TraceResult.Conclusion.*
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure.TraceResult.TraceStatus
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset.CompletionContext
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.permission.Approval
import de.fruxz.sparkle.framework.permission.hasApproval
import org.bukkit.entity.Player
import java.util.*
import kotlin.time.Duration

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

	fun trace(executor: InterchangeExecutor, rawInput: List<String>): TraceResult<EXECUTOR, InterchangeStructure<EXECUTOR>> = TraceResult(
		base = this,
		query = rawInput,
		traced = buildMap<TraceStatus, MutableSet<TraceWay<InterchangeStructure<EXECUTOR>>>> {

			// Place every default value
			putAll(TraceStatus.values().associateWith { mutableSetOf() })

			// Provide recursive trace function

			fun inner(currentBranch: InterchangeStructure<EXECUTOR>, currentDepth: Int, parentStatus: TraceStatus) {
				println("inner ${currentBranch.address}")
				if (currentDepth > MAX_TRACE_DEPTH) {
					debugLog("Trace depth of $MAX_TRACE_DEPTH exceeded!")
					return
				}

				debugLog("tracing branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentStatus")

				val localInput = rawInput.getOrNull(currentDepth - 1) ?: ""
				val localInputAccepted = currentBranch.validInput(executor, localInput, rawInput)
				val isLocalExecutableRoot = currentBranch.isRoot && (currentBranch.onExecution != null || currentBranch.configuration.isRequired)

				// Starting trace

				val localStatus: TraceStatus = when (parentStatus) {
					TraceStatus.FAILED -> parentStatus.also { println("${currentBranch.address} parent is $it") }
					TraceStatus.NO_DESTINATION, TraceStatus.MATCHING, TraceStatus.INCOMPLETE, TraceStatus.OVERFLOW -> {

						when {
							!currentBranch.userRestriction.match(executor) -> TraceStatus.FAILED.also { System.err.println(1) } // user restriction not met
							!currentBranch.requiredApprovals.all { executor.hasApproval(it) } -> TraceStatus.FAILED.also { System.err.println(2) } // not enough of the required approvals!
							isLocalExecutableRoot && rawInput.isEmpty() -> TraceStatus.MATCHING.also { System.err.println(3) } // root is executable and input is not accepted/existing
							!localInputAccepted -> {
								when {
									localInput.isBlank() -> TraceStatus.INCOMPLETE.also { System.err.println(4) }
									currentBranch.parent?.isRoot == true && rawInput.isEmpty() -> TraceStatus.INCOMPLETE.also { System.err.println(5) }
									else -> TraceStatus.FAILED.also { System.err.println(6) }
								}
							}

							currentBranch.subBranches.any { it.configuration.isRequired } && rawInput.lastIndex < (currentDepth - 1) -> TraceStatus.INCOMPLETE.also { System.err.println(7) }
							currentDepth < rawInput.lastIndex && !currentBranch.configuration.infiniteSubParameters -> TraceStatus.OVERFLOW.also { System.err.println(8) }
							currentBranch.subBranches.isNotEmpty() && currentBranch.subBranches.all { it.configuration.isRequired } && currentBranch.onExecution == null -> TraceStatus.NO_DESTINATION.also { System.err.println(9) }
							else -> TraceStatus.MATCHING.also { System.err.println(10) }
						}

					}
				}

				// End trace -> process result

				debugLog("branch ${currentBranch.identity}[${currentBranch.address}] with depth '$currentDepth' from parentStatus $parentStatus is $localStatus")

				this[localStatus]!!.add(
					TraceWay(
						branch = currentBranch,
						cachedCompletion = currentBranch.computeLocalCompletion(
							context = CompletionContext(
								executor = executor,
								fullLineInput = rawInput,
								input = localInput,
								ignoreCase = currentBranch.configuration.ignoreCase,
							)
						),
						depth = currentDepth,
						usedQuery = rawInput,
					)
				)

				// process result -> continue trace with sub-branches

				currentBranch.subBranches.forEach {
					inner(it, currentDepth + 1, localStatus)
				}

			}

			// Start tracing

			inner(this@InterchangeStructure, 0, TraceStatus.MATCHING)

		}
	)

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

		enum class Conclusion {

			/**
			 * Found the perfect result.
			 * ***ONE POSSIBLE WAY***
			 */
			RESULT,

			/**
			 * Many possible results.
			 * ***MANY POSSIBLE WAYS***
			 */
			MULTIPLE_RESULTS,

			/**
			 * No result, invalid request.
			 * ***NO POSSIBLE WAY***
			 */
			NO_RESULT,

			/**
			 * No ways to trace found (0 possible ways), only possible if the traceBase has no sub-branches.
			 */
			EMPTY;

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
		val cachedCompletion: List<String>,
		val depth: Int,
		val usedQuery: List<String>,
	)

	fun validateInput(parameters: List<String>, executor: InterchangeExecutor): Boolean =
		trace(executor, parameters).conclusion == RESULT

	suspend fun performExecution(access: InterchangeAccess<out InterchangeExecutor>): InterchangeResult {
		return trace(access.executor, access.parameters).let { trace ->
			when (trace.traced[TraceStatus.MATCHING]!!.size.maxTo(2)) {
				0, 2 -> WRONG_USAGE.also { println("wrong usage") }
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
		val filteredContent = tracingContent.filter { it.depth == parameters.size }
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
		identity: String = "${(parent?.identity ?: "")}/${subBranches.size}",
		path: Address<InterchangeStructure<EXECUTOR>> = this.address / "${subBranches.size}",
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
	path: Address<InterchangeStructure<EXECUTOR>> = Address.address("root"),
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