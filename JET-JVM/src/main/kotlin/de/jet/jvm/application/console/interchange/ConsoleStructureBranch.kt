@file:Suppress("unused")

package de.jet.jvm.application.console.interchange

import de.jet.jvm.interchange.InterchangeStructureBranch
import de.jet.jvm.tool.smart.Producible

open class ConsoleStructureBranch(
    override val branchName: String,
    override val path: String,
    override val branches: List<ConsoleStructureBranch> = emptyList(),
    open val content: ((parameters: List<String>) -> Unit)? = null,
) : InterchangeStructureBranch(branchName, path, branches) {

    data class Builder(
        var branchName: String,
        var path: String,
        var branches: MutableList<ConsoleStructureBranch> = mutableListOf(),
        var content: ((parameters: List<String>) -> Unit)? = null,
    ) : Producible<ConsoleStructureBranch> {

        operator fun plus(branch: ConsoleStructureBranch) = apply {
            branches.add(branch)
        }

        fun branch(name: String, process: Builder.() -> Unit = { }) = apply {
            branches.add(Builder(name, "$path/$name", branches, content).apply(process).produce())
        }

        fun content(content: ((parameters: List<String>) -> Unit)?) = apply {
            this.content = content
        }

        override fun produce() = ConsoleStructureBranch(
            branchName,
            path,
            branches,
            content
        )
    }
}