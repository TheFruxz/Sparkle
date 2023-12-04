package dev.fruxz.sparkle.server.component.sandox

import org.bukkit.command.CommandSender

data class SandBoxCommandContext(
    val sandbox: SandBoxManager.SandBox,
    val source: CommandSender,
    val input: SandBoxManager.SandBoxInput,
)