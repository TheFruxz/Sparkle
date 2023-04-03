package dev.fruxz.sparkle.framework.command.sparkle

import dev.fruxz.sparkle.framework.command.bukkit.TabCommandExecutor
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command : CommandBranch(), TabCommandExecutor {
    private var isConfigured = false

    abstract fun configure()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        runConfiguration()

        val trace = this.executeTrace(CommandExecutionContext(sender, command, label, args.toList()))

        trace.destinationContext?.let { trace.destination?.execution?.invoke(it) }

        trace.error?.let { sender.sendMessage("§cerror: ${trace.error.name}") }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        runConfiguration()

        return generateTabCompletion(CommandExecutionContext(sender, command, label, args.toList()))
    }

    private fun runConfiguration() {
        if (!isConfigured) {
            this.configure()
            this.lock()
            isConfigured = true
        }
    }

}