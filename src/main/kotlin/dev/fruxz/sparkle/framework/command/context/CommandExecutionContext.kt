package dev.fruxz.sparkle.framework.command.context

import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

data class CommandExecutionContext(
    override val executor: CommandSender,
    override val command: BukkitCommand,
    override val label: String,
    override val parameters: List<String>,
) : CommandContext
