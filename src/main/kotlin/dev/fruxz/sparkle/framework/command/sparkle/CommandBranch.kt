package dev.fruxz.sparkle.framework.command.sparkle

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
    fun flatMapBranches(): List<CommandBranch> = branches.flatMap { it.flatMapBranches() }

    fun branchInputCheck(context: CommandContext, branchInput: List<String>): InputCheckResult {
        val rawCompletions = content.flatMap { it.generateTab(context.asBranchContext(this, branchInput)) }
        val distinctCompletions = rawCompletions.distinct()
        val duplicateAmount = rawCompletions.size - distinctCompletions.size

        if (duplicateAmount > 0) mainLogger.warning("Command ${context.command} has $duplicateAmount overlaying completions on the same level")

        if (!configuration.multiWord && branchInput.any { it.contains(" ") }) return ILLEGAL_MULTI_WORD.also {
            System.err.println("!!! with '$branchInput'")
        }
        if (configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT) return IGNORE_CONTENT

        println("IC.input '$branchInput'")

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
        var currentBranch: CommandBranch? = this
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
                                TraceResult.NOT_REACHED to flatMapBranches(),
                            )
                        )
                    }
                }

                else -> {

                    currentBranch?.branches?.forEach { tracedBranch ->
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
                            openEndBranch -> localInputCopy.drop(currentDepth).also { println("open: $it") }
                            else -> listOf(currentInput).also { println("closed: $it") }
                        }

                        val isLastDepth = currentDepth == processedInput.lastIndex

                        println("trace@depth=$currentDepth, branch=${tracedBranch.generateBranchDisplay()}, input=$nextBranchInput, lastDepth=$isLastDepth")

                        tracedBranch.branchInputCheck(
                            context = context.asBranchContext(
                                branch = tracedBranch,
                                branchInput = nextBranchInput
                            ), branchInput = nextBranchInput
                        ).also { inputCheckResult ->

                            if (lastDepthOfRoute) {

                                addResult(
                                    TraceResult.NOT_REACHED,
                                    tracedBranch.flatMapBranches()
                                )

                            }

                            when {
                                inputCheckResult.failure || inputCheckResult.partialSuccess -> {
                                    addResult(TraceResult.FAILED, tracedBranch)
                                    addResult(
                                        TraceResult.OUT_OF_VIEW,
                                        tracedBranch.flatMapBranches()
                                    )
                                }
                            }

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
                            }

                            if (isLastDepth) {

                                when (tracedBranch.execution) {
                                    null -> addResult(
                                        TraceResult.NO_EXECUTION,
                                        tracedBranch,
                                    )
                                }

                                addResult(
                                    TraceResult.NOT_REACHED,
                                    tracedBranch.flatMapBranches()
                                ) // TODO check, if correct, just placed because it looks like it should be here
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
        val traceableInput = processedInput.dropLast(1)

        System.err.println("input: ${context.parameters.joinToString { "'$it'" }} ")
        System.err.println("traceable: ${traceableInput.joinToString { "'$it'" }}")

        val traceResult = trace(object : CommandContext {
            override val executor = context.executor
            override val command = context.command
            override val label: String = context.label
            override val parameters: List<String> = traceableInput
        }, split = false)

        println("traceResult: $traceResult")

        if (traceResult is FailedCommandBranchTrace) {
            return emptyList<String>().also { println("no destination") }
        }

        traceResult as SuccessfulCommandBranchTrace // indicate that traceResult is indeed a SuccessfulCommandBranchTrace

        val tracedBranch = traceResult.hit.destination

        println("tracedBranch: ${tracedBranch.generateBranchDisplay()}")

        return tracedBranch.branches.flatMap { branch ->
            val ignoreCase = branch.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE

            branch.content.flatMap { branchContent ->
                branchContent.generateTab(context.asBranchContext(tracedBranch, listOf("")))
                    .let { completions -> // TODO is emptyList() the right thing? | edit: replaced with listOf(""), solution?
                        completions.mapNotNull {
                            var content = it

                            if (branch.configuration.multiWord && content.contains(" ")) {
                                content = "\"$content\""
                            }

                            if (
                                content.equals(lastInput, ignoreCase = ignoreCase) ||
                                content.startsWith(lastInput, ignoreCase = ignoreCase) ||
                                (branch.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT || content.contains(
                                    lastInput,
                                    ignoreCase = ignoreCase
                                ))
                            ) {
                                return@mapNotNull content
                            } else return@mapNotNull null
                        }
                    }
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
         * @property MATCH the branch was found
         * @property OVERFLOW the branch was not hit because the input was too long
         * @property NOT_REACHED the branch was not hit because the input was too short
         * @property NO_EXECUTION the branch was not hit because it has no execution set
         * @property OUT_OF_VIEW the branch was not hit because it is out of view (other branches, which was not successful)
         */
        enum class TraceResult {
            HIT,
            MATCH,
            OVERFLOW,
            NOT_REACHED,
            NO_EXECUTION,
            OUT_OF_VIEW,
            FAILED;
        }

    }

}

fun String.joinArgumentChunks(spliterator: String = "\""): List<String> { // TODO replace the thing in ascend with this cool stuff!
    val splitted = this.split(" ").takeIf { this.isNotBlank() } ?: emptyList()

    System.err.println("splitted: [${splitted.joinToString("|")}] @ ${splitted.size}")

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

    }.also { System.err.println("split size: ${it.size}") }
}

typealias BranchExecution = (BranchExecutionContext.() -> Unit)