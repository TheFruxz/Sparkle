@file:Suppress("unused", "DEPRECATION")

package de.moltenKt.paper.extension.paper

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.core.tool.smart.identification.Identifiable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

val consoleSender: ConsoleCommandSender
	get() = Bukkit.getConsoleSender()

val onlinePlayers: Collection<Player>
	get() = Bukkit.getOnlinePlayers()

val offlinePlayers: List<OfflinePlayer>
	get() = Bukkit.getOfflinePlayers().toList()

fun player(playerName: String) = Bukkit.getPlayer(playerName)

fun player(uniqueIdentity: UUID) = Bukkit.getPlayer(uniqueIdentity)

fun player(identity: Identifiable<out OfflinePlayer>) = player(UUID.fromString(identity.identity))

@Suppress("DEPRECATION")
fun offlinePlayer(playerName: String) = Bukkit.getOfflinePlayer(playerName)

fun offlinePlayer(uniqueIdentity: UUID) = Bukkit.getOfflinePlayer(uniqueIdentity)

fun offlinePlayer(identity: Identifiable<out OfflinePlayer>) = offlinePlayer(UUID.fromString(identity.identity))

fun Plugin.createKey(key: String) = NamespacedKey(this, key)

val templateLocation: Location
	get() = Bukkit.getWorlds().first().spawnLocation

val bukkitVersion: String
	get() = Bukkit.getBukkitVersion()

fun getWorld(name: String) = Bukkit.getWorld(name)

fun getWorld(uid: UUID) = Bukkit.getWorld(uid)

fun getWorld(worldKey: NamespacedKey) = Bukkit.getWorld(worldKey)

val worlds: List<World>
	get() = Bukkit.getWorlds()

val server: Server
	get() = Bukkit.getServer()