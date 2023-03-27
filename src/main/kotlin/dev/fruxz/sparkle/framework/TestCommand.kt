package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.annotation.command.CommandName
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@CommandName("test")
class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        sender.sendMessage("Hey! The command works!")
        return true
    }
}