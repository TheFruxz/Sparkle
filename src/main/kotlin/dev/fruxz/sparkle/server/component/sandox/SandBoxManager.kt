package dev.fruxz.sparkle.server.component.sandox

import dev.fruxz.sparkle.framework.system.debugLog

object SandBoxManager {

    internal var sandboxes = mapOf<String, SandBox>()

    operator fun contains(label: String): Boolean = sandboxes.containsKey(label)

    operator fun get(label: String): SandBox? = sandboxes[label]

    operator fun set(label: String, block: suspend (SandBoxCommandContext) -> Unit) {
        if (sandboxes.containsKey(label)) throw IllegalArgumentException("SandBox with label $label already exists")

        this += SandBox(label, block)

    }

    operator fun plusAssign(sandbox: SandBox) {
        if (sandboxes.containsKey(sandbox.label)) throw IllegalArgumentException("SandBox with label ${sandbox.label} already exists")

        sandboxes += sandbox.label to sandbox

    }

    operator fun minusAssign(label: String) {
        if (!sandboxes.containsKey(label)) throw IllegalArgumentException("SandBox with label $label does not exist")

        sandboxes -= label

    }

    @JvmInline
    value class SandBoxInput(val greedyString: String) {

        fun asString() = greedyString

        fun asParameters() = greedyString.split(" ")

        fun isAvailable() = greedyString.isNotBlank()

        companion object {

            val EMPTY = SandBoxInput("")

        }

    }

    data class SandBox(
        val label: String,
        val process: suspend (SandBoxCommandContext) -> Unit,
    ) {

        suspend operator fun invoke(context: SandBoxCommandContext) {
            debugLog("Invoking code of sandbox '$label'")
            process.invoke(context)
        }

    }

}