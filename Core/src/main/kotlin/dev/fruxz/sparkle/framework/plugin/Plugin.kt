package dev.fruxz.sparkle.framework.plugin

import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.pluginManager
import net.kyori.adventure.key.Key
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

@SparkleDSL
fun SparklePlugin(plugin: dev.fruxz.sparkle.framework.SparklePlugin.() -> Unit) = object : dev.fruxz.sparkle.framework.SparklePlugin(plugin) { }

val plugins: Set<Plugin>
    get() = pluginManager.plugins.toSet()

/**
 * This function accesses the plugin instance of the given plugin [T] or null if it is not loaded.
 * @see PluginManager.getPlugins
 */
@SparkleDSL
inline fun <reified T : Plugin> pluginOrNull(): T? = pluginManager.plugins.firstOrNull { it is T } as T?

/**
 * This function accesses the plugin instance of the given plugin [T] or throws an exception if it is not loaded.
 * @see PluginManager.getPlugins
 */
@SparkleDSL
@Throws(NoSuchElementException::class)
inline fun <reified T : Plugin> plugin(): T = pluginOrNull<T>() ?: throw NoSuchElementException("Plugin ${T::class.simpleName} not found!")

fun pluginOrNull(name: String): Plugin? = pluginManager.getPlugin(name)

@Throws(NoSuchElementException::class)
fun plugin(name: String): Plugin = pluginOrNull(name) ?: throw NoSuchElementException("Plugin $name not found!")

// Key

fun Plugin.key() = Key.key(this.name)


