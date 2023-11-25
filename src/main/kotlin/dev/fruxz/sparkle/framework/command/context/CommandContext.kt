package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.stacked.extension.api.StyledString
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.Command as BukkitCommand

interface CommandContext {

    val performer: CommandSender

    val command: BukkitCommand

    val label: String

    val parameters: List<String>

    // interaction
    fun reply(@StyledString text: String) = performer.sendMessage(text(text).colorIfAbsent(NamedTextColor.GRAY))

    fun reply(message: Component) = performer.sendMessage(message.colorIfAbsent(NamedTextColor.GRAY))

    // player access
    
    val player: Player
        get() = performer as Player

    val playerOrNull: Player?
        get() = performer as? Player

    // transformation

    fun asCommandContext(): CommandExecutionContext {
        return CommandExecutionContext(
            performer = performer,
            command = command,
            label = label,
            parameters = parameters
        )
    }

}