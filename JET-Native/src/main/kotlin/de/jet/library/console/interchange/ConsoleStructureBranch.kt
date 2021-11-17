package de.jet.library.console.interchange

import de.jet.library.interchange.InterchangeStructureBranch
import de.jet.library.tool.smart.Producible

class ConsoleStructureBranch(
    override val branchName: String,
    override val branches: List<ConsoleStructureBranch> = emptyList(),
    val content: ((parameters: List<String>) -> Unit)? = null,
) : InterchangeStructureBranch(branchName, branches) {

    data class Builder(
        var branchName: String,
        var branches: MutableList<ConsoleStructureBranch> = mutableListOf(),
        var content: ((parameters: List<String>) -> Unit)? = null,
    ) : Producible<ConsoleStructureBranch> {

        operator fun plus(branch: ConsoleStructureBranch) = apply {
            branches.add(branch)
        }

        fun branch(name: String, process: Builder.() -> Unit = { }) = apply {
            branches.add(Builder(name).apply(process).produce())
        }

        fun content(content: ((parameters: List<String>) -> Unit)?) = apply {
            this.content = content
        }

        override fun produce() = ConsoleStructureBranch(
            branchName,
            branches,
            content
        )
    }
}