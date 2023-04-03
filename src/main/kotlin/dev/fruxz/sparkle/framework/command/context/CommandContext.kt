package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

interface CommandContext {

    val executor: CommandSender

    val command: BukkitCommand

    val label: String

    val parameters: List<String>

    fun asBranchContext(branch: CommandBranch, branchInput: List<String>): BranchExecutionContext {
        return BranchExecutionContext(
            executor = executor,
            command = command,
            label = label,
            parameters = parameters,
            branch = branch,
            branchParameters = branchInput
        )
    }

    fun asCommandContext(): CommandExecutionContext {
        return CommandExecutionContext(
            executor = executor,
            command = command,
            label = label,
            parameters = parameters
        )
    }

}