package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

data class BranchExecutionContext(
    override val executor: CommandSender,
    override val command: Command,
    override val label: String,
    override val parameters: List<String>,
    val branch: CommandBranch,
    val branchParameters: List<String>,
) : CommandContext