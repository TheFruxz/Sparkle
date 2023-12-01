package dev.fruxz.sparkle.framework.command.context

import dev.fruxz.brigadikt.activity.BrigadiktCommandContext
import dev.fruxz.stacked.extension.api.StyledString
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// interaction
fun <S : CommandSender> BrigadiktCommandContext<S>.reply(@StyledString text: String) = source.sendMessage(text(text).colorIfAbsent(NamedTextColor.GRAY))

fun <S : CommandSender> BrigadiktCommandContext<S>.reply(message: Component) = source.sendMessage(message.colorIfAbsent(NamedTextColor.GRAY))

// player access

val <S : CommandSender> BrigadiktCommandContext<S>.player: Player
    get() = source as Player

val <S : CommandSender> BrigadiktCommandContext<S>.playerOrNull: Player?
    get() = source as? Player