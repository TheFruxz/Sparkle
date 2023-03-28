package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.annotation.command.properties.Aliases
import dev.fruxz.sparkle.framework.annotation.command.properties.Description
import dev.fruxz.sparkle.framework.annotation.command.properties.Label
import dev.fruxz.sparkle.framework.annotation.command.properties.Usage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@Label("test")
@Description("This is a test command, to test the command system.")
@Usage("/test and nothing else!")
@Aliases(["testsus", "testcommand"])
class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        sender.sendMessage("Hey! The command works!")
        return true
    }
}