package dev.fruxz.sparkle.framework.command.context

import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

data class CommandExecutionContext(
    override val performer: CommandSender,
    override val command: BukkitCommand,
    override val label: String,
    override val parameters: List<String>,
) : CommandContext
