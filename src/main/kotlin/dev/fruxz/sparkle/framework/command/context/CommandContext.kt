package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

interface CommandContext {

    val executor: CommandSender

    val command: BukkitCommand

    val label: String

    val parameters: List<String>

    fun asBranchContext(branch: CommandBranch, branchParameters: List<String>): BranchExecutionContext { // TODO branch parameter should may be only a string, with the ' ' contained
        return BranchExecutionContext(
            executor,
            command,
            label,
            parameters,
            branch,
            branchParameters
        )
    }

    fun asCommandContext(): CommandExecutionContext {
        return CommandExecutionContext(
            executor,
            command,
            label,
            parameters
        )
    }

}