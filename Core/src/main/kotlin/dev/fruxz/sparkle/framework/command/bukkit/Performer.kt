package dev.fruxz.sparkle.framework.command.bukkit

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

typealias CommandPerformer = CommandSender

// player

val CommandPerformer.asPlayerOrNull: Player?
    get() = this as? Player

val CommandPerformer.asPlayer: Player
    get() = this as Player

val CommandPerformer.isPlayer: Boolean
    get() = this is Player

// console

val CommandPerformer.asConsoleOrNull: ConsoleCommandSender?
    get() = this as? ConsoleCommandSender

val CommandPerformer.asConsole: ConsoleCommandSender
    get() = this as ConsoleCommandSender

val CommandPerformer.isConsole: Boolean
    get() = this is ConsoleCommandSender
