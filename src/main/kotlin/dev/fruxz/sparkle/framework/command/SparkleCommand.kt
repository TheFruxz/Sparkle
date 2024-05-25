package dev.fruxz.sparkle.framework.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.command.bukkit.TabCommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class SparkleCommand : TabCommandExecutor {

    lateinit var plugin: SparklePlugin

    abstract val command: LiteralArgumentBuilder<CommandSender>

    override fun onCommand(sender: CommandSender, command: Command, label: String, arguments: Array<out String>): Boolean {
        try {
            plugin.commandDispatcher.execute((arrayOf(command.label) + arguments).joinToString(" "), sender)
        } catch (e: CommandSyntaxException) {
            sender.sendMessage(e.displayMessageComponent())
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        arguments: Array<out String>,
    ): List<String> {
        return plugin.commandDispatcher.getCompletionSuggestions(
            plugin.commandDispatcher.parse((arrayOf(command.label) + arguments).joinToString(" "), sender)
        ).join().list.map { it.text }
    }

}