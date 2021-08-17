package de.jet.library.extension.paper

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*

val consoleSender: ConsoleCommandSender
	get() = Bukkit.getConsoleSender()

val onlinePlayers: Collection<Player>
	get() = Bukkit.getOnlinePlayers()

fun getPlayer(playerName: String) = Bukkit.getPlayer(playerName)

fun getPlayer(uniqueIdentity: UUID) = Bukkit.getPlayer(uniqueIdentity)

@Suppress("DEPRECATION")
fun getOfflinePlayer(playerName: String) = Bukkit.getOfflinePlayer(playerName)

fun getOfflinePlayer(uniqueIdentity: UUID) = Bukkit.getOfflinePlayer(uniqueIdentity)

fun Plugin.createKey(key: String) = NamespacedKey(this, key)

val templateLocation: Location
	get() = Bukkit.getWorlds().first().spawnLocation
