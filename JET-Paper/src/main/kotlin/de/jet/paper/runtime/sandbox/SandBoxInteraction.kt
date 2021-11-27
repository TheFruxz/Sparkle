package de.jet.paper.runtime.sandbox

import org.bukkit.command.CommandSender

data class SandBoxInteraction internal constructor(
    val sandBox: SandBox,
    val executor: CommandSender,
    val parameters: List<String>,
)