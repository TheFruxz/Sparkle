package de.jet.library.console.interchange

import de.jet.library.interchange.InterchangeStructure
import de.jet.library.tool.smart.Producible
import de.jet.library.tool.smart.positioning.Address

class ConsoleInterchange(
    override val identity: String,
    override val branches: List<ConsoleStructureBranch>,
    val content: ((parameters: List<String>) -> Unit)? = null,
) : InterchangeStructure<ConsoleStructureBranch>(identity) {

    fun performInterchange(input: String): Boolean {
        val nearest = getNearestBranchWithParameters(Address.address(input.removePrefix("$identity ").replace(" ", "/")))

        println("n: ${nearest?.first?.address?.addressString}")

        return if (nearest != null) {

            val content = nearest.first.value.content

            if (content != null) {

                content(nearest.second.split(" "))

                true

            } else
                false

        } else
            false

    }

    data class Builder(
        var identity: String,
        var branches: MutableList<ConsoleStructureBranch> = mutableListOf(),
        var content: ((parameters: List<String>) -> Unit)? = null,
    ) : Producible<ConsoleInterchange> {

        operator fun plus(branch: ConsoleStructureBranch) = apply {
            branches.add(branch)
        }

        fun branch(name: String, process: ConsoleStructureBranch.Builder.() -> Unit = { }) = apply {
            branches.add(ConsoleStructureBranch.Builder(name).apply(process).produce())
        }

        fun content(content: ((parameters: List<String>) -> Unit)?) = apply {
            this.content = content
        }

        override fun produce() = ConsoleInterchange(
            identity, branches, content
        )

    }

}