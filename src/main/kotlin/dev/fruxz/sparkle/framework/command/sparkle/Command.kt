package dev.fruxz.sparkle.framework.command.sparkle

import dev.fruxz.sparkle.framework.command.bukkit.TabCommandExecutor
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command : CommandBranch(branchDepth = -1), TabCommandExecutor {
    private var isConfigured = false

    abstract fun configure()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        runConfiguration()

        val new = this.trace(CommandExecutionContext(sender, command, label, args.toList()))

        println("result------------------")
        println(new.result.toList().joinToString {
            it.first.name + ": \n " + it.second.joinToString { "\t" + it.generateBranchDisplay() + "\n" }
        })
        println("result------------------")

        if (new is SuccessfulCommandBranchTrace) {
            new.hit.destination.execution?.invoke(new.hit.context) ?: println("no execution")
        }

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