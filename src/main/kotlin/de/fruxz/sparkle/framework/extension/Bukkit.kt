@file:Suppress("unused")

package de.fruxz.sparkle.framework.extension

import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.asStyledComponent
import de.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.structure.StructureManager
import java.util.*

/**
 * This value represents the [ConsoleCommandSender] (the console).
 * This uses the [Bukkit.getConsoleSender] function and is initialized lazily.
 * @author Fruxz
 * @since 1.0
 */
val consoleSender: ConsoleCommandSender by lazy { Bukkit.getConsoleSender() }

/**
 * This computational value returns every online-player
 * inside a [Set] of [Player]s.
 * This utilizes the [Bukkit.getOnlinePlayers] function
 * and converts it into a set using the [Collection.toSet]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val onlinePlayers: Set<Player>
	get() = Bukkit.getOnlinePlayers().toSet()

/**
 * This computational value returns every offline-player
 * inside a [Set] of [OfflinePlayer]s.
 * **This operation can be quite performance-intensive, if
 * you had a lot of unique players on your server, you should
 * directly use the [Bukkit.getOfflinePlayers] function!**
 * This utilizes the [Bukkit.getOfflinePlayers] function
 * and converts it into a set using the [Array.toSet]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val offlinePlayers: Set<OfflinePlayer>
	get() = Bukkit.getOfflinePlayers().toSet()

/**
 * This computational value returns every [onlinePlayers] and the [consoleSender] inside
 * a [Set] of [InterchangeExecutor]s.
 * @author Fruxz
 * @since 1.0
 */
val everyone: Set<InterchangeExecutor>
	get() = (onlinePlayers + consoleSender)

/**
 * This computational value returns the current [Bukkit.getStructureManager].
 * @author Fruxz
 * @since 1.0
 */
val structureManager: StructureManager
	get() = Bukkit.getStructureManager()

/**
 * This function searches for an online-[Player], which
 * has the given [playerName] as name.
 * This returns the found online-[Player], or null, if
 * no player with the given name is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(playerName: String) = Bukkit.getPlayer(playerName)

/**
 * This function searches for an online-[Player], which
 * has the given [uniqueIdentity] as unique identity.
 * This returns the found online-[Player], or null, if
 * no player with the given unique identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(uniqueIdentity: UUID) = Bukkit.getPlayer(uniqueIdentity)

/**
 * This function searches for an online-[Player], which
 * has the given [identity] as identity.
 * This returns the found online-[Player], or null, if
 * no player with the given identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(identity: Identifiable<out OfflinePlayer>) = playerOrNull(UUID.fromString(identity.identity))

/**
 * This function searches for an online-[Player], which
 * has the given [playerName] as name.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * name is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(playerName: String) = playerOrNull(playerName) ?: throw NoSuchElementException("Player '$playerName'(Name) not found")

/**
 * This function searches for an online-[Player], which
 * has the given [uniqueIdentity] as unique identity.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * unique identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(uniqueIdentity: UUID) = playerOrNull(uniqueIdentity) ?: throw NoSuchElementException("Player '$uniqueIdentity'(UUID) not found")

/**
 * This function searches for an online-[Player], which
 * has the given [identity] as identity.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(identity: Identifiable<out OfflinePlayer>) = playerOrNull(identity) ?: throw NoSuchElementException("Player '$identity'(Identity) not found")

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [playerName] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(playerName: String) = Bukkit.getOfflinePlayer(playerName)

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [uniqueIdentity] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(uniqueIdentity: UUID) = Bukkit.getOfflinePlayer(uniqueIdentity)

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [identity] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(identity: Identifiable<out OfflinePlayer>) = offlinePlayer(UUID.fromString(identity.identity))

/**
 * This function creates a [NamespacedKey] with the given
 * [key] on [this] [Plugin]s base.
 * @author Fruxz
 * @since 1.0
 */
fun Plugin.createNamespacedKey(key: String): NamespacedKey = NamespacedKey(this, key)

fun App.createKey(value: String): Key = key.subKey(value, CONTINUE)

/**
 * This function searches for a [World] with the given
 * [worldName] as name.
 * This returns the found [World], or null, if no
 * world with the given name is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(worldName: String) = Bukkit.getWorld(worldName)

/**
 * This function searches for a [World] with the given
 * [uniqueIdentity] as unique identity.
 * This returns the found [World], or null, if no
 * world with the given unique identity is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(uniqueIdentity: UUID) = Bukkit.getWorld(uniqueIdentity)

/**
 * This function searches for a [World] with the given
 * [worldKey] as key.
 * This returns the found [World], or null, if no
 * world with the given key is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(worldKey: NamespacedKey) = Bukkit.getWorld(worldKey)

/**
 * This function searches for a [World] with the given
 * [worldName] as name.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * name is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(worldName: String) = worldOrNull(worldName) ?: throw NoSuchElementException("World '$worldName'(Name) not found")

/**
 * This function searches for a [World] with the given
 * [uniqueIdentity] as unique identity.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * unique identity is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(uniqueIdentity: UUID) = worldOrNull(uniqueIdentity) ?: throw NoSuchElementException("World '$uniqueIdentity'(UUID) not found")

/**
 * This function searches for a [World] with the given
 * [worldKey] as key.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * key is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(worldKey: NamespacedKey) = worldOrNull(worldKey) ?: throw NoSuchElementException("World '$worldKey'(Key) not found")

/**
 * This computational value returns the [World.getSpawnLocation]
 * of the [worlds] first [World].
 * This helps to quickly get an easy-to-use [Location], that
 * doesn't have to be something particularly special.
 * Commonly used as parameter- & config-defaults
 * @author Fruxz
 * @since 1.0
 */
val templateLocation: Location
	get() = worlds.first().spawnLocation

/**
 * This computational value returns a [List] of [World]s,
 * using the [Bukkit.getWorlds] function.
 * @author Fruxz
 * @since 1.0
 */
val worlds: List<World>
	get() = Bukkit.getWorlds()

/**
 * This computational value returns the [Server], on which
 * this app is currently running, using the [Bukkit.getServer]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val server: Server
	get() = Bukkit.getServer()

/**
 * This function searches for a [Plugin] with the given [name] as name. This returns the found
 * plugin, or null if no plugin with the given name is found.
 * @author Fruxz
 * @since 1.0
 */
fun pluginOrNull(name: String) = Bukkit.getPluginManager().getPlugin(name)

/**
 * This function searches for a [Plugin] with the given [name] as name. This returns the found
 * plugin, or throws an [NoSuchElementException] if no plugin with the given name is found.
 * @author Fruxz
 * @since 1.0
 */
fun plugin(name: String) = pluginOrNull(name) ?: NoSuchElementException("Plugin '$name'(Name) not found")

/**
 * This function broadcasts the [component] to every individual player and console on the server.
 * If the [permission] is not blank, only everyone having the given permission will receive the message.
 * @author Fruxz
 * @since 1.0
 * @see Bukkit.broadcast
 * @see String.asStyledComponent
 */
fun broadcast(component: ComponentLike, permission: String = "") = when (permission.isBlank()) {
	true -> Bukkit.broadcast(component.asComponent())
	false -> Bukkit.broadcast(component.asComponent(), permission)
}

/**
 * This function broadcasts the [styledMessage] to every individual player and console on the server.
 * If the [permission] is not blank, only everyone having the given permission will receive the message.
 * @author Fruxz
 * @since 1.0
 * @see Bukkit.broadcast
 * @see String.asStyledComponent
 */
fun broadcast(styledMessage: String, permission: String = "") = when (permission.isBlank()) {
	true -> Bukkit.broadcast(styledMessage.asStyledComponent)
	false -> Bukkit.broadcast(styledMessage.asStyledComponent, permission)
}

/**
 * This function broadcasts the [component] to every individual player and console on the server via the actionbar.
 * If the [permission] is not blank, only everyone having the given permission will receive the message.
 * @author Fruxz
 * @since 1.0
 * @see Player.sendActionBar
 * @see String.asStyledComponent
 */
fun broadcastActionBar(component: ComponentLike, permission: String = "") = when (permission.isBlank()) {
	true -> onlinePlayers.forEach { it.sendActionBar(component.asComponent()) }
	false -> onlinePlayers.forEach { if (it.hasPermission(permission)) it.sendActionBar(component.asComponent()) }
}

/**
 * This function broadcasts the [styledMessage] to every individual player and console on the server via the actionbar.
 * If the [permission] is not blank, only everyone having the given permission will receive the message.
 * @author Fruxz
 * @since 1.0
 * @see Player.sendActionBar
 * @see String.asStyledComponent
 */
fun broadcastActionBar(styledMessage: String, permission: String = "") = when (permission.isBlank()) {
	true -> onlinePlayers.forEach { it.sendActionBar(styledMessage.asStyledComponent) }
	false -> onlinePlayers.forEach { if (it.hasPermission(permission)) it.sendActionBar(styledMessage.asStyledComponent) }
}

