package de.jet.library.extension.paper

import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

val consoleSender: ConsoleCommandSender
	get() = Bukkit.getConsoleSender()

val onlinePlayers: Collection<Player>
	get() = Bukkit.getOnlinePlayers()
