package dev.fruxz.sparkle.framework.system

import dev.fruxz.sparkle.framework.adventure.namespacedKey
import dev.fruxz.stacked.extension.api.StyledString
import dev.fruxz.stacked.extension.asStyledComponent
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

// server

val pluginManager by lazy { Bukkit.getPluginManager() }

val consoleSender by lazy { Bukkit.getConsoleSender() }

val structureManager by lazy { Bukkit.getStructureManager() }

val itemFactory by lazy { Bukkit.getItemFactory() }

// path

val pluginsFolder by lazy { Bukkit.getPluginsFolder().toPath() }

// player properties

val onlinePlayers: Set<Player>
    get() = Bukkit.getOnlinePlayers().toSet()

val offlinePlayers: Set<OfflinePlayer>
    get() = Bukkit.getOfflinePlayers().toSet()

val allPlayers: Set<OfflinePlayer>
    get() = onlinePlayers + offlinePlayers

// player functions

fun playerOrNull(name: String) = Bukkit.getPlayer(name)

fun playerOrNull(uuid: UUID) = Bukkit.getPlayer(uuid)

@Throws(NoSuchElementException::class)
fun player(name: String) = playerOrNull(name) ?: throw NoSuchElementException("Player $name not found!")

@Throws(NoSuchElementException::class)
fun player(uuid: UUID) = playerOrNull(uuid) ?: throw NoSuchElementException("Player $uuid not found!")

fun offlinePlayer(name: String) = Bukkit.getOfflinePlayer(name)

fun offlinePlayer(uuid: UUID) = Bukkit.getOfflinePlayer(uuid)

fun offlinePlayerIfCachedOrNull(name: String) = Bukkit.getOfflinePlayerIfCached(name)

@Throws(NoSuchElementException::class)
fun offlinePlayerIfCached(name: String) = offlinePlayerIfCachedOrNull(name) ?: throw NoSuchElementException("Player $name not cached!")

// world properties

val worlds: List<World>
    get() = Bukkit.getWorlds()

// world functions

fun worldOrNull(name: String) = Bukkit.getWorld(name)

fun worldOrNull(uuid: UUID) = Bukkit.getWorld(uuid)

fun worldOrNull(key: Key) = Bukkit.getWorld(key.namespacedKey)

@Throws(NoSuchElementException::class)
fun world(name: String) = worldOrNull(name) ?: throw NoSuchElementException("World $name not found!")

@Throws(NoSuchElementException::class)
fun world(uuid: UUID) = worldOrNull(uuid) ?: throw NoSuchElementException("World $uuid not found!")

@Throws(NoSuchElementException::class)
fun world(key: Key) = worldOrNull(key) ?: throw NoSuchElementException("World $key not found!")

// location properties

val templateLocation: Location
    get() = Location(null, 0.0, 0.0, 0.0)

// message functions

fun broadcast(component: ComponentLike, permission: String?) = when (permission.isNullOrBlank()) {
    true -> Bukkit.broadcast(component.asComponent())
    else -> Bukkit.broadcast(component.asComponent(), permission)
}

fun broadcast(@StyledString message: String, permission: String?) =
    broadcast(component = message.asStyledComponent, permission)

fun broadcastActionBar(component: ComponentLike, permission: String?) {
    onlinePlayers.forEach { target ->
        if (permission.isNullOrBlank() || target.hasPermission(permission)) { target.sendActionBar(component) }
    }
}

fun broadcastActionBar(@StyledString message: String, permission: String?) =
    broadcastActionBar(component = message.asStyledComponent, permission)

