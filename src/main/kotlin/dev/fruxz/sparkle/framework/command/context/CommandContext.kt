package dev.fruxz.sparkle.framework.command.context

import de.fruxz.stacked.text
import dev.fruxz.sparkle.framework.command.sparkle.CommandBranch
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

interface CommandContext {

    val executor: CommandSender

    val command: BukkitCommand

    val label: String

    val parameters: List<String>

    // interaction
    fun reply(text: String) = executor.sendMessage(text(text))

    fun reply(message: Component) = executor.sendMessage(message)

    // transformation
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