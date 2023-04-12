package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.ascend.extension.math.minTo
import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.command.context.CommandContext
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.InputCheckResult.*
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.SuccessfulCommandBranchTrace.TraceResult
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.SuccessfulCommandBranchTrace.TraceResult.*
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.system.mainLogger

open class CommandBranch(val parent: CommandBranch? = null, val branchDepth: Int = 0) {

    val branches: MutableSet<CommandBranch> =
        mutableSetOf()

    val content: MutableList<BranchContent<*>> =
        mutableListOf()

    var execution: BranchExecution? = null

    var isLocked = false

    var configuration: BranchConfiguration = BranchConfiguration()

    // building

    @SparkleDSL
    fun branch(builder: CommandBranch.() -> Unit) {
        lockWarning("add branch")

        if (configuration.openEnd) mainLogger.warning("LOGIC ISSUE! You're creating a branch from an openEnd-configured-branch, this is not recommended!")

        branches.add(
            CommandBranch(
                parent = this,
                branchDepth = branchDepth + 1
            ).apply(builder)
        )
    }

    @SparkleDSL
    fun content(vararg content: BranchContent<*>) {
        lockWarning("add content")
        this.content.addAll(content)
    }

    @SparkleDSL
    fun content(vararg static: String) {
        lockWarning("add content")
        this.content.addAll(static.map { BranchContent.static(it) })
    }

    @SparkleDSL
    fun execution(execution: BranchExecution) {
        lockWarning("add execution")
        this.execution = execution
    }

    // configuration

    @SparkleDSL
    fun configureMatchMode(mode: BranchConfiguration.MatchMode) {
        lockWarning("configureMatchMode")
        this.configuration = this.configuration.copy(matchMode = mode)
    }

    @SparkleDSL
    fun configureIgnoreCase() = configureMatchMode(BranchConfiguration.MatchMode.IGNORE_CASE)

    @SparkleDSL
    fun configureIgnoreContent() = configureMatchMode(BranchConfiguration.MatchMode.IGNORE_CONTENT)

    @SparkleDSL
    fun configureMultiWord(allowed: Boolean = true) {
        lockWarning("configureMultiWord")
        this.configuration = this.configuration.copy(multiWord = allowed)
    }

    @SparkleDSL
    fun configureOpenEnd(openEnd: Boolean = true) {
        lockWarning("configureOpenEnd")
        this.configuration = this.configuration.copy(openEnd = openEnd)
    }

    // internal processing

    fun generateBranchDisplay(): String = when (parent) {
        null -> "#root"
        else -> {
            val isContentIgnored = this.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT

            "$branchDepth$" + content.joinToString("/") {
                if (isContentIgnored) "<${it.key.asString()}>" else "[${it.generateDisplay()}]"
            }
        }
    }

    fun executePathTrace() = buildList {
        var current: CommandBranch? = this@CommandBranch
        while (current != null) {
            add(0, current)
            current = current.parent
        }
    }

    /**
     * Not containing this branch, only its [branches] and their [branches] and so on.
     */
    fun flatMapBranches(): List<CommandBranch> = branches.toList() + branches.flatMap { it.flatMapBranches() }

    fun branchInputCheck(context: CommandContext, branchInput: List<String>): InputCheckResult {
        val rawCompletions = content.flatMap { it.generateTab() }
        val distinctCompletions = rawCompletions.distinct()
        val duplicateAmount = rawCompletions.size - distinctCompletions.size

        if (duplicateAmount > 0) mainLogger.warning("Command ${context.command} has $duplicateAmount overlaying completions on the same level")

        if (!configuration.multiWord && branchInput.any { it.contains(" ") }) return ILLEGAL_MULTI_WORD
        if (configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT) return IGNORE_CONTENT

        val other = branchInput.firstOrNull() ?: throw IllegalArgumentException("Branch input must not be empty")
        val ignoreCase = this.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE

        return when {
            rawCompletions.any { it.equals(other = other, ignoreCase = ignoreCase) } -> EQUALS
            rawCompletions.any {
                it.startsWith(
                    prefix = other,
                    ignoreCase = ignoreCase
                )
            } -> STARTS_WITH

            rawCompletions.any { it.contains(other = other, ignoreCase = ignoreCase) } -> CONTAINS
            else -> NO_MATCH
        }

    }

    // The new trace
    fun trace(context: CommandContext, split: Boolean = true): CommandTrace {
        val processedInput = when {
            split -> context.parameters.joinToString(" ").joinArgumentChunks()
            else -> context.parameters
        }.toMutableList()

        val producedResults = mutableMapOf<TraceResult, List<CommandBranch>>()
        var currentBranch: CommandBranch = this
        var currentDepth = -1

        fun addResult(result: TraceResult, vararg branch: CommandBranch) {
            if (branch.isEmpty()) return
            producedResults[result] = producedResults[result].orEmpty() + branch
        }

        fun addResult(result: TraceResult, branches: List<CommandBranch>) {
            if (branches.isEmpty()) return
            producedResults[result] = producedResults[result].orEmpty() + branches
        }

        fun isAlreadyProcessed(branch: CommandBranch): Boolean {
            return producedResults.values.any { it.contains(branch) }
        }

        while (currentDepth <= processedInput.lastIndex) { // changed to while, because lastDepth will change!
            debugLog { "@ depth = $currentDepth" }
            debugLog { "@ last = ${processedInput.lastIndex} due to '${processedInput.joinToString { "'$it'" }}'" }
            debugLog { "@ execution = $execution" }

            when (currentDepth) {
                -1 -> {
                    if (processedInput.lastIndex == currentDepth && execution != null) {
                        debugLog { "execution hit" }
                        return SuccessfulCommandBranchTrace(
                            hit = SuccessfulCommandBranchTrace.TraceHit(
                                destination = this,
                                context = context.asBranchContext(
                                    this,
                                    processedInput
                                ), // TODO check if this is even used!
                                path = this.executePathTrace(),
                            ),
                            processedInput = processedInput,
                            result = mapOf(
                                TraceResult.HIT to listOf(this),
                                // TODO TraceResult.NOT_REACHED to currentBranch.flatMapBranches(),
                            )
                        )
                    }
                }

                else -> {

                    currentBranch.branches.forEach { tracedBranch ->
                        if (isAlreadyProcessed(tracedBranch)) return@forEach

                        val multiWordBranch = tracedBranch.configuration.multiWord
                        val openEndBranch = tracedBranch.configuration.openEnd
                        val lastDepthOfRoute = tracedBranch.branches.isEmpty() // TODO this is not correct <-!-
                        val localInputCopy = processedInput.toMutableList()
                        var currentInput = localInputCopy[currentDepth]

                        // process non-multiWordBranches

                        if (!multiWordBranch && currentInput.contains(" ")) { // if multi-word-input is used, without being supported on the branch
                            localInputCopy.removeAt(currentDepth)
                            localInputCopy.addAll(
                                currentDepth,
                                "\"$currentInput\"".split(" ")
                            ) // replace the single multi-worded-input with multiple single-worded-inputs, to restore default behavior
                            currentInput = localInputCopy[currentDepth] // update the currentInput, because now it is only one word
                        } else if (!multiWordBranch && openEndBranch) {
                            var localInputDepth = 0

                            while (localInputDepth <= localInputCopy.lastIndex) {
                                val localInput = localInputCopy[localInputDepth]
                                if (localInput.contains(" ")) {
                                    localInputCopy.removeAt(localInputDepth)
                                    localInputCopy.addAll(
                                        localInputDepth,
                                        "\"$localInput\"".split(" ")
                                    ) // replace the single multi-worded-input with multiple single-worded-inputs, to restore default behavior
                                }
                                localInputDepth++
                            }
                        }

                        // process openEndBranches

                        val nextBranchInput = when {
                            openEndBranch -> localInputCopy.drop(currentDepth)
                            else -> listOf(currentInput)
                        }

                        val isLastDepth = currentDepth == processedInput.lastIndex

                        debugLog { "trace@depth=$currentDepth, branch=${tracedBranch.generateBranchDisplay()}, input=$nextBranchInput, lastDepth=$isLastDepth" }

                        tracedBranch.branchInputCheck(
                            context = context.asBranchContext(
                                branch = tracedBranch,
                                branchInput = nextBranchInput
                            ), branchInput = nextBranchInput
                        ).also { inputCheckResult ->

                            when {
                                inputCheckResult.success -> {

                                    addResult(
                                        when {
                                            isLastDepth || (lastDepthOfRoute && openEndBranch) -> HIT
                                            else -> {
                                                currentBranch = tracedBranch // TODO in the right place?
                                                TraceResult.MATCH
                                            }
                                        }, tracedBranch
                                    )

                                    // update processed input
                                    processedInput.clear()
                                    processedInput.addAll(localInputCopy)

                                }
                                else -> {
                                    if (currentDepth == localInputCopy.lastIndex) {
                                        addResult(FAILED, tracedBranch)
                                        addResult(NOT_REACHED, tracedBranch.flatMapBranches())
                                    } else {
                                        addResult(OVERFLOW, tracedBranch)
                                    }
                                }
                            }

                        }

                    }

                }
            }

            currentDepth++
        }

        // return the processed result

        val successfulBranch =
            producedResults[HIT]?.takeIf { it.size == 1 }?.first() ?: producedResults[HIT]?.firstOrNull()?.also {
                mainLogger.warning("[Trace] Multiple branches matched the input on by the following branches ${producedResults[HIT]?.joinToString { "'${it.generateBranchDisplay()}'" }} @ depth = ${it.branchDepth}!")
            }
        return when (successfulBranch) {
            null -> FailedCommandBranchTrace(producedResults, processedInput)
            else -> SuccessfulCommandBranchTrace(
                hit = SuccessfulCommandBranchTrace.TraceHit(
                    destination = successfulBranch,
                    context = context.asBranchContext(
                        successfulBranch,
                        processedInput.drop(successfulBranch.branchDepth)
                    ).copy(parameters = processedInput),
                    path = successfulBranch.executePathTrace(),
                ),
                processedInput = processedInput,
                result = producedResults,
            )
        }

    }

    fun generateTabCompletion(context: CommandExecutionContext): List<String> {
        val processedInput = context.parameters.joinToString(" ").joinArgumentChunks()
        val lastInput = processedInput.lastOrNull() ?: ""
        val traceResult = trace(object : CommandContext {
            override val executor = context.executor
            override val command = context.command
            override val label: String = context.label
            override val parameters: List<String> = processedInput
        }, split = false)

        debugLog {
            """
                capture------------------
                ${traceResult.result.toList().joinToString { 
                    it.first.name + ": \n " + it.second.joinToString { "\t'" + it.generateBranchDisplay() + "'\n" }
                }}
                capture------------------    
            """.trimIndent()
        }

        return when (context.parameters.firstOrNull()?.isBlank()) {
            true -> this.branches // if no input
            else -> traceResult.result[FAILED].orEmpty()
        }.filter { it.branchDepth == processedInput.lastIndex.minTo(0) }
            .flatMap { it.content.flatMap(BranchContent<*>::generateTab) }
            .mapNotNull {
                when {
                    it.contains(" ") -> {
                        "\"$it\""
                    }
                    else -> it
                }.let { output ->
                    output.takeIf { output.contains(lastInput, true) }
                }
            }

    }

    internal fun lock() {
        isLocked = true
        branches.forEach { it.lock() }
    }

    private fun lockWarning(modification: String? = null) {
        if (isLocked) mainLogger.warning(
            "CommandBranch ${executePathTrace().joinToString("/") { it.generateBranchDisplay() }} is locked but a modification ${
                modification.takeIf { it != null }?.let { "'$it' " } ?: ""
            }has been done anyway! Check your code!")
    }

    /**
     * @param matchMode How the match-check is made
     * @param multiWord is `"multiple words"` allowed
     * @param openEnd (only allowed at last) if there are infinite additional parameters allowed
     */
    data class BranchConfiguration(
        val matchMode: MatchMode = MatchMode.MATCH_CONTENT,
        val multiWord: Boolean = false,
        val openEnd: Boolean = false,
    ) {

        enum class MatchMode {
            MATCH_CONTENT, IGNORE_CASE, IGNORE_CONTENT;
        }

    }

    /**
     * @property EQUALS the input matches one tab-completion
     * @property STARTS_WITH the input only starts with one tab-completion, but does not match it
     * @property CONTAINS the input only contains one tab-completion, but does not match it
     * @property NO_MATCH the input does not match any tab-completion
     * @property ILLEGAL_MULTI_WORD the input is a multi-worded input, but the branch does not support multi-worded inputs
     * @property IGNORE_CONTENT the input is not checked for content because the branch is configured to ignore content (checks)
     */
    enum class InputCheckResult {
        EQUALS,
        STARTS_WITH,
        CONTAINS,
        NO_MATCH,
        ILLEGAL_MULTI_WORD,
        IGNORE_CONTENT;

        /**
         * If the input-check was successful (either [EQUALS] or [IGNORE_CONTENT])
         */
        val success: Boolean by lazy {
            this == EQUALS || this == IGNORE_CONTENT
        }

        /**
         * If the input-check was successful, but not a full match (either [STARTS_WITH] or [CONTAINS])
         */
        val partialSuccess: Boolean by lazy {
            this == STARTS_WITH || this == CONTAINS
        }

        /**
         * If the input-check was not successful (either [NO_MATCH] or [ILLEGAL_MULTI_WORD])
         */
        val failure: Boolean by lazy {
            this == NO_MATCH || this == ILLEGAL_MULTI_WORD
        }

    }

    sealed interface CommandTrace {
        val result: Map<TraceResult, List<CommandBranch>>
        val processedInput: List<String>
    }

    data class FailedCommandBranchTrace(
        override val result: Map<TraceResult, List<CommandBranch>>,
        override val processedInput: List<String>,
    ) : CommandTrace

    data class SuccessfulCommandBranchTrace(
        val hit: TraceHit,
        override val processedInput: List<String>,
        override val result: Map<TraceResult, List<CommandBranch>>
    ) : CommandTrace {

        data class TraceHit(
            val destination: CommandBranch,
            val context: BranchExecutionContext,
            val path: List<CommandBranch>,
        )

        /**
         * @property HIT the branch was hit and was the end-point
         * @property MATCH the branch was found
         * @property FAILED the check failed
         * @property NOT_REACHED the branch was not reached at all (after a [FAILED])
         */
        enum class TraceResult {
            HIT,
            MATCH,

            FAILED,
            OVERFLOW,
            NOT_REACHED,
        }

    }

}

fun String.joinArgumentChunks(spliterator: String = "\""): List<String> { // TODO replace the thing in ascend with this cool stuff!
    val splitted = this.split(" ").takeIf { this.isNotBlank() } ?: emptyList()

    return buildList {
        var isQuoted = false
        val current = StringBuilder()

        for (string in splitted) {
            if (string.startsWith(spliterator)) {
                isQuoted = true
                current.append(string.removePrefix(spliterator))
            } else if (string.endsWith(spliterator)) {
                if (isQuoted) current.append(" ")

                isQuoted = false
                current.append(string.removeSuffix(spliterator))
                add(current.toString())
                current.clear()
            } else if (isQuoted) {
                current.append(" ").append(string)
            } else {
                add(string)
            }
        }

        if (current.isNotEmpty()) addAll((spliterator + current).split(" "))

    }
}

typealias BranchExecution = (suspend BranchExecutionContext.() -> Unit)