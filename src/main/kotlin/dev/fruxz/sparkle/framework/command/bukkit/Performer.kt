package dev.fruxz.sparkle.framework.command.bukkit

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

// player

val CommandSender.asPlayerOrNull: Player?
    get() = this as? Player

val CommandSender.asPlayer: Player
    get() = this as Player

val CommandSender.isPlayer: Boolean
    get() = this is Player

// console

val CommandSender.asConsoleOrNull: ConsoleCommandSender?
    get() = this as? ConsoleCommandSender

val CommandSender.asConsole: ConsoleCommandSender
    get() = this as ConsoleCommandSender

val CommandSender.isConsole: Boolean
    get() = this is ConsoleCommandSender
