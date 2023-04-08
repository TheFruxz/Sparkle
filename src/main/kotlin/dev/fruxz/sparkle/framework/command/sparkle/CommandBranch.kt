package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.ascend.annotation.ExperimentalAscendApi
import de.fruxz.ascend.extension.container.joinedLast
import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.command.context.CommandContext
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.InputCheckResult.*
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.InputCheckResult.MATCH
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.SuccessfulCommandBranchTrace.TraceResult.*
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch.SuccessfulCommandBranchTrace.TraceResult.MATCH
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

        branches.add(CommandBranch(
            parent = this,
            branchDepth = branchDepth + 1
        ).apply(builder))
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

    fun generateBranchDisplayString(): String = buildString {
        if (parent != null) {
            append(content.joinToString("|") { it.key.asString() }) // TODO content should also have displayString in future
        } else
            append("#root")
    }

    @Deprecated("new implementation")
    fun executeInputCheck(context: CommandContext, branchInput: List<String>): Boolean {
        val rawTabCompletions = content.flatMap { it.tabGenerator.invoke(context.asBranchContext(this, branchInput)) }
        val distinctCompletions = rawTabCompletions.distinct()
        val duplicateAmount = rawTabCompletions.size - distinctCompletions.size

        if (duplicateAmount > 0) mainLogger.warning("Command ${context.command} has $duplicateAmount overlaying completions on the same level")

        if (!configuration.multiWord && branchInput.any { it.contains(" ") }) return false
        if (configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT) return true

        println("IC.input '$branchInput'")

        return rawTabCompletions.any {
            println("IC.check '$branchInput' against '$it'")
            it.equals(
                other = branchInput.firstOrNull().also { if (it == null) println("empty first input") },
                ignoreCase = this.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE
            )
        }
    }

    fun executePathTrace() = buildList {
        var current: CommandBranch? = this@CommandBranch
        while (current != null) {
            add(0, current)
            current = current.parent
        }
    }

    @Deprecated("new implementation")
    fun executeTrace(context: CommandContext, split: Boolean = true): TraceResult {
        val processedInput = (if (split) context.parameters.joinToString(" ").newSplitArguments() else context.parameters).also {
            println("input: ${context.parameters.joinToString { "'$it'" }} ")
            println("processed: ${it.joinToString { "'$it'" }}")
        }.toMutableList()

        var currentBranch: CommandBranch? = this
        var lastDepth = processedInput.lastIndex
        var traceError: TraceError? = null

        for (depth in (-1)..lastDepth) {

            println("depth: $depth")

            if (depth == -1) {
                println("entry 0")
                println("lastDepth = $lastDepth")
                println("execution !is null = ${execution != null}")

                if (lastDepth == depth && execution != null) {
                    return TraceResult(
                        destination = this,
                        destinationContext = context.asBranchContext(this, processedInput), // TODO check whether or not this is even ever used!
                        path = this.executePathTrace(),
                        error = traceError
                    )
                }

            } else {
                var currentInput = processedInput[depth]
                var triggeredOpenEnd = false

                println("entry 1")
                println("current: $currentInput")

                var processedInputBackup = processedInput.toList()

                // TODO seems to be removed, we just simply disallow the use of "..."
                fun reverseArgumentSplitParagraphMerge(levelInput: List<String>) = levelInput.flatMap {
                    if (it.contains(" ")) {
                        val reversed = "\"$it\"".split(" ")

                        System.err.println("old: ${processedInput.size}")

                        processedInput.removeAt(depth) // remove the old one
                        processedInput.addAll(depth, reversed) // place the reversed ones in the same position
                        lastDepth = processedInput.lastIndex // update the last index
                        currentInput = processedInput[depth] // update the current input

                        System.err.println("new: ${processedInput.size}")

                        reversed
                    } else listOf(it)
                }

                fun reverseToBackup() {
                    System.err.println("reverseToBackup")
                    processedInput.clear()
                    processedInput.addAll(processedInputBackup)
                    lastDepth = processedInput.lastIndex
                    currentInput = processedInput[depth]
                }

                val nextBranch = currentBranch?.branches?.firstOrNull {
                    val openEndBranch = it.configuration.openEnd
                    val multiWordBranch = it.configuration.multiWord

                    System.err.println("Current level input: $currentInput")

                    if (!multiWordBranch) {
                        System.err.println("Branch is not multiWord")
                        reverseArgumentSplitParagraphMerge(emptyList()) // start refactoring here
                    }

                    val nextBranchInput = when {
                        openEndBranch -> processedInput.drop(depth)
                        else -> listOf(element = processedInput[depth])
                    }

                    it.executeInputCheck(
                        context = context.asBranchContext(
                            branch = it,
                            branchInput = nextBranchInput
                        ), branchInput = nextBranchInput // TODO OpenEndMode.CHECK_FIRST, CHECK_NONE | edit: idee verworfen | edit: vielleicht doch wichtig
                    ).also { inputCheckResult ->
                        when {
                            inputCheckResult -> {
                                processedInputBackup = processedInput
                                System.err.println("Override backup")

                                if (openEndBranch) {
                                    triggeredOpenEnd = true
                                }
                            }
                            else -> {
                                if (!multiWordBranch) {
                                    reverseToBackup() // revert the changes of refactoring
                                }
                            }
                        }
                    }

                }

                if (nextBranch != null) {
                    currentBranch = nextBranch
                } else {
                    if (!triggeredOpenEnd) {
                        currentBranch = null // Prevent the run of 'this' branch, even if traceError. Due to the default value of currentBranch, which is 'this'
                        traceError = TraceError.NO_MATCH
                        System.err.println(3)
                    }
                }

                if (depth == lastDepth || triggeredOpenEnd) {
                    if (currentBranch != null) {
                        if (currentBranch.execution != null) {

                            val branchContext = BranchExecutionContext(
                                executor = context.executor,
                                command = context.command,
                                label = context.label,
                                parameters = processedInput,
                                branch = currentBranch,
                                branchParameters = when {
                                    triggeredOpenEnd -> processedInput.drop(depth) // <- look! openEnd support :D
                                    else -> listOf(element = currentInput.orEmpty())
                                },
                            )

                            return TraceResult(
                                destination = currentBranch,
                                destinationContext = branchContext,
                                path = currentBranch.executePathTrace(),
                                error = traceError
                            )
                        } else {
                            traceError = TraceError.NO_EXECUTION
                            System.err.println(2)
                        }
                    } else {
                        traceError = TraceError.NO_MATCH
                        System.err.println(1)
                    }
                } else {
                    // TODO traceError = TraceError.TOO_MANY_POSSIBILITIES seems not to be needed / fit
                    System.err.println(0)
                }

            }

        }

        return TraceResult(currentBranch, null, currentBranch?.executePathTrace() ?: emptyList(), traceError)
    }

    /**
     * Not containing this branch, only its [branches] and their [branches] and so on.
     */
    fun flatMapBranches(): Set<CommandBranch> = branches.flatMap { it.flatMapBranches() }.toSet()

    fun branchInputCheck(context: CommandContext, branchInput: List<String>): InputCheckResult {
        val rawCompletions = content.flatMap { it.tabGenerator.invoke(context.asBranchContext(this, branchInput)) }
        val distinctCompletions = rawCompletions.distinct()
        val duplicateAmount = rawCompletions.size - distinctCompletions.size

        if (duplicateAmount > 0) mainLogger.warning("Command ${context.command} has $duplicateAmount overlaying completions on the same level")

        if (!configuration.multiWord && branchInput.any { it.contains(" ") }) return InputCheckResult.ILLEGAL_MULTI_WORD // in most cases this should never happen due to the preprocessing of the input
        if (configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT) return InputCheckResult.IGNORE_CONTENT

        println("IC.input '$branchInput'")

        val other = branchInput.firstOrNull() ?: throw IllegalArgumentException("Branch input must not be empty")
        val ignoreCase = this.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE

        return when {
            rawCompletions.any { it.equals(other = other, ignoreCase = ignoreCase) } -> InputCheckResult.MATCH
            rawCompletions.any { it.startsWith(prefix = other, ignoreCase = ignoreCase) } -> InputCheckResult.STARTS_WITH
            rawCompletions.any { it.contains(other = other, ignoreCase = ignoreCase) } -> InputCheckResult.PARTIAL_MATCH
            else -> InputCheckResult.NO_MATCH
        }

    }

    // The new trace
    fun trace(context: CommandContext, split: Boolean = true): CommandTrace {
        val processedInput = when {
            split -> context.parameters.joinToString(" ").newSplitArguments()
            else -> context.parameters
        }.toMutableList()

        // TODO for the next work at his project: maybe a mutableMap<Result, List<branch>> to process it during the 'trace', collect and after all return it
        val producedResults = mutableMapOf<SuccessfulCommandBranchTrace.TraceResult, Set<CommandBranch>>()
        var currentBranch: CommandBranch? = this
        var traceError: TraceError? = null
        var currentDepth = -1

        fun addResult(result: SuccessfulCommandBranchTrace.TraceResult, vararg branch: CommandBranch) {
            producedResults[result] = producedResults[result].orEmpty() + branch
        }

        while (currentDepth <= processedInput.lastIndex) { // changed to while, because lastDepth will change!
            debugLog { "@ depth = $currentDepth" }

            when (currentDepth) {
                -1 -> {
                    if (processedInput.lastIndex == currentDepth && execution != null) {
                        return SuccessfulCommandBranchTrace(
                            hit = SuccessfulCommandBranchTrace.TraceHit(
                                destination = this,
                                context = context.asBranchContext(this, processedInput), // TODO check if this is even used!
                                path = this.executePathTrace(),
                            ),
                            result = mapOf(SuccessfulCommandBranchTrace.TraceResult.NOT_REACHED to flatMapBranches())
                        )
                    }
                }
                else -> {

                    currentBranch?.branches?.forEach { tracedBranch ->
                        val multiWordBranch = tracedBranch.configuration.multiWord
                        val openEndBranch = tracedBranch.configuration.openEnd
                        val lastDepthOfRoute = tracedBranch.branches.isEmpty() // TODO this is not correct <-!-
                        val localInputCopy = processedInput.toMutableList()
                        var currentInput = localInputCopy[currentDepth]

                        // process non-multiWordBranches

                        if (!multiWordBranch && currentInput.contains(" ")) { // if multi-word-input is used, without being supported on the branch
                            localInputCopy.removeAt(currentDepth)
                            localInputCopy.addAll(currentDepth, "\"$currentInput\"".split(" ")) // replace the single multi-worded-input with multiple single-worded-inputs, to restore default behavior
                            currentInput = localInputCopy[currentDepth] // update the currentInput, because now it is only one word
                        }

                        // process openEndBranches

                        val nextBranchInput = when {
                            openEndBranch -> localInputCopy.drop(currentDepth)
                            else -> listOf(currentInput)
                        }

                        tracedBranch.branchInputCheck(
                            context = context.asBranchContext(
                                branch = tracedBranch,
                                branchInput = nextBranchInput
                            ), branchInput = nextBranchInput
                        ).also { inputCheckResult ->

                            if (lastDepthOfRoute) {

                                when {
                                    tracedBranch.execution == null -> addResult(SuccessfulCommandBranchTrace.TraceResult.NO_EXECUTION, tracedBranch)
                                    inputCheckResult.success -> addResult(SuccessfulCommandBranchTrace.TraceResult.MATCH, tracedBranch)
                                }

                                addResult(SuccessfulCommandBranchTrace.TraceResult.NOT_REACHED, *tracedBranch.branches.toTypedArray())

                            } else {

                                when {
                                    inputCheckResult.failure || inputCheckResult.partialSuccess -> {
                                        addResult(SuccessfulCommandBranchTrace.TraceResult.FAILED, tracedBranch)
                                        addResult(SuccessfulCommandBranchTrace.TraceResult.OUT_OF_VIEW, *tracedBranch.branches.toTypedArray())
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

        return when (val successfulBranch = producedResults[SuccessfulCommandBranchTrace.TraceResult.MATCH]?.takeIf { it.size == 1 }?.first()) {
            null -> FailedCommandBranchTrace(producedResults)
            else -> SuccessfulCommandBranchTrace(
                hit = SuccessfulCommandBranchTrace.TraceHit(
                    destination = successfulBranch,
                    context = context.asBranchContext(successfulBranch, processedInput), // TODO branchInput, not the whole input! (branches store their depth, maybe this helps!)
                    path = successfulBranch.executePathTrace(),
                ),
                result = producedResults,
            )
        }

    }

    fun generateTabCompletion(context: CommandExecutionContext): List<String> {
        val processedInput = context.parameters.joinToString(" ").newSplitArguments()
        val lastInput = processedInput.lastOrNull() ?: ""
        val traceableInput = processedInput.dropLast(1)

        System.err.println("input: ${context.parameters.joinToString { "'$it'" }} ")
        System.err.println("traceable: ${traceableInput.joinToString { "'$it'" }}")

        val tracedBranch = executeTrace(object : CommandContext {
            override val executor = context.executor
            override val command = context.command
            override val label: String = context.label
            override val parameters: List<String> = traceableInput
        }, split = false).destination ?: return emptyList<String>().also { println("no destination") }

        println("tracedBranch: ${tracedBranch.generateBranchDisplayString()}")

        return tracedBranch.branches.flatMap { branch ->
            val ignoreCase = branch.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE

            branch.content.flatMap { branchContent ->
                branchContent.tabGenerator.invoke(context.asBranchContext(tracedBranch, listOf(""))).let { completions -> // TODO is emptyList() the right thing? | edit: replaced with listOf(""), solution?
                    completions.mapNotNull {
                        var content = it

                        if (branch.configuration.multiWord && content.contains(" ")) {
                            content = "\"$content\""
                        }

                        if (
                            content.equals(lastInput, ignoreCase = ignoreCase) ||
                            content.startsWith(lastInput, ignoreCase = ignoreCase) ||
                            (branch.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT || content.contains(lastInput, ignoreCase = ignoreCase))
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
            "CommandBranch ${executePathTrace().joinToString("/") { it.generateBranchDisplayString() }} is locked but a modification ${
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

    @Deprecated("new system in dev")
    data class TraceResult(
        val destination: CommandBranch?,
        val destinationContext: BranchExecutionContext?,
        val path: List<CommandBranch>,
        val error: TraceError?,
    )

    enum class TraceError {
        NO_MATCH,
        NO_EXECUTION,
        TOO_MANY_POSSIBILITIES,
    }

    /**
     * @property MATCH the input matches one tab-completion
     * @property STARTS_WITH the input only starts with one tab-completion, but does not match it
     * @property PARTIAL_MATCH the input only contains one tab-completion, but does not match it
     * @property NO_MATCH the input does not match any tab-completion
     * @property ILLEGAL_MULTI_WORD the input is a multi-worded input, but the branch does not support multi-worded inputs
     * @property IGNORE_CONTENT the input is not checked for content because the branch is configured to ignore content (checks)
     */
    enum class InputCheckResult {
        MATCH,
        STARTS_WITH,
        PARTIAL_MATCH,
        NO_MATCH,
        ILLEGAL_MULTI_WORD,
        IGNORE_CONTENT;

        /**
         * If the input-check was successful (either [MATCH] or [IGNORE_CONTENT])
         */
        val success: Boolean by lazy {
            this == MATCH || this == IGNORE_CONTENT
        }

        /**
         * If the input-check was successful, but not a full match (either [STARTS_WITH] or [PARTIAL_MATCH])
         */
        val partialSuccess: Boolean by lazy {
            this == STARTS_WITH || this == PARTIAL_MATCH
        }

        /**
         * If the input-check was not successful (either [NO_MATCH] or [ILLEGAL_MULTI_WORD])
         */
        val failure: Boolean by lazy {
            this == NO_MATCH || this == ILLEGAL_MULTI_WORD
        }

    }

    sealed interface CommandTrace {
        val result: Map<SuccessfulCommandBranchTrace.TraceResult, Set<CommandBranch>>
    }

    data class FailedCommandBranchTrace(
        override val result: Map<SuccessfulCommandBranchTrace.TraceResult, Set<CommandBranch>>,
    ) : CommandTrace

    data class SuccessfulCommandBranchTrace(
        val hit: TraceHit,
        override val result: Map<TraceResult, Set<CommandBranch>>
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
            MATCH,
            OVERFLOW,
            NOT_REACHED,
            NO_EXECUTION,
            OUT_OF_VIEW,
            FAILED;
        }

    }

}

@OptIn(ExperimentalAscendApi::class)
fun String.newSplitArguments() = split("\"") // TODO ascend api <- do not forget to push
    .let { it.takeIf { it.size % 2 != 0 } ?: it.joinedLast(1, "\"") }
    .flatMapIndexed { index: Int, value: String ->
        if (index % 2 == 0) value.split(" ") else listOf(value)
    }
    .withIndex().filterNot { it.value.isBlank() && it.index % 2 == 0 }.map { it.value }

typealias BranchExecution = (BranchExecutionContext.() -> Unit)