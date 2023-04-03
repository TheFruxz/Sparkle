package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.ascend.annotation.ExperimentalAscendApi
import de.fruxz.ascend.extension.container.joinedLast
import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.command.context.CommandContext
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.mainLogger

open class CommandBranch(val parent: CommandBranch? = null) {

    val branches: MutableList<CommandBranch> =
        mutableListOf()

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

        branches.add(CommandBranch(parent = this).apply(builder))
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

    fun executeInputCheck(context: CommandContext, input: String): Boolean {
        val tabCompletions = content.flatMap { it.tabGenerator.invoke(context.asBranchContext(this, input.split(" "))) }
        val distinctCompletions = tabCompletions.distinct()
        val duplicateAmount = tabCompletions.size - distinctCompletions.size

        if (duplicateAmount > 0) mainLogger.warning("Command ${context.command} has $duplicateAmount overlaying completions on the same level")

        if (!configuration.multiWord && input.contains(" ")) return false
        if (configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CONTENT) return true

        println("IC.input '$input'")

        return tabCompletions.any {
            println("IC.check '$input' against '$it'")
            it.equals(
                input,
                ignoreCase = this.configuration.matchMode == BranchConfiguration.MatchMode.IGNORE_CASE
            ) // TODO if ignoreCase at the branches as configurations | edit: seems now fixed, but the ignore_content have to be added too!
        }
    }

    fun executePathTrace() = buildList {
        var current: CommandBranch? = this@CommandBranch
        while (current != null) {
            add(0, current)
            current = current.parent
        }
    }

    fun executeTrace(context: CommandContext, split: Boolean = true): TraceResult {
        val processedInput = (if (split) context.parameters.joinToString(" ").newSplitArguments() else context.parameters).also {
            println("input: ${context.parameters.joinToString { "'$it'" }} ")
            println("processed: ${it.joinToString { "'$it'" }}")
        }

        var currentBranch: CommandBranch? = this
        val lastDepth = processedInput.lastIndex
        var traceError: TraceError? = null

        for (depth in (-1)..lastDepth) {

            println("depth: $depth")

            if (depth == -1) {
                println("entry 0")
                println("lastDepth = $lastDepth")
                println("execution !is null = ${execution != null}")

                if (lastDepth == depth && execution != null) {
                    return TraceResult(
                        this,
                        context.asBranchContext(this, processedInput),
                        this.executePathTrace(),
                        traceError
                    )
                }

            } else {
                val currentInput = processedInput.getOrNull(depth)
                var triggeredOpenEnd = false

                println("entry 1")
                println("current: $currentInput")

                val nextBranch = currentBranch?.branches?.firstOrNull {
                    val openEndBranch = it.configuration.openEnd
                    val nextBranchInput = when {
                        openEndBranch -> processedInput.drop(depth).joinToString(" ")
                        else -> processedInput[depth]
                    }

                    it.executeInputCheck(
                        context = context.asBranchContext(
                            branch = it,
                            branchParameters = nextBranchInput.split(" ")
                        ), input = processedInput[depth] // TODO OpenEndMode.CHECK_FIRST, CHECK_NONE | edit: idee verworfen
                    ).also { inputCheckResult ->
                        if (inputCheckResult && openEndBranch) {
                            triggeredOpenEnd = true
                        }
                    }

                }

                if (nextBranch != null) {
                    currentBranch = nextBranch
                } else {
                    if (!triggeredOpenEnd) {
                        currentBranch = null // prevent the run of 'this' branch, even if traceError. Due to the default value of currentBranch, which is 'this'
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
                                    triggeredOpenEnd -> processedInput.drop(depth)
                                    else -> currentInput?.split(" ") ?: emptyList()
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

    @OptIn(ExperimentalAscendApi::class)
    fun String.newSplitArguments() = split("\"")
        .let { it.takeIf { it.size % 2 != 0 } ?: it.joinedLast(1, "\"") }
        .flatMapIndexed { index: Int, value: String ->
            if (index % 2 == 0) value.split(" ") else listOf(value)
        }
        .withIndex().filterNot { it.value.isBlank() && it.index % 2 == 0 }.map { it.value }

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
                branchContent.tabGenerator.invoke(context.asBranchContext(tracedBranch, emptyList())).let { completions -> // TODO is emptyList() the right thing?
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

}

typealias BranchExecution = (BranchExecutionContext.() -> Unit)