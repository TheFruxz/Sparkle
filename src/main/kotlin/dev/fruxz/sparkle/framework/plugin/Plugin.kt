package dev.fruxz.sparkle.framework.plugin

import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

@SparkleDSL
fun SparklePlugin(plugin: SparklePlugin.() -> Unit) = object : SparklePlugin(plugin) { }

// Instance access

val pluginManager by lazy { Bukkit.getPluginManager() }

/**
 * This function accesses the plugin instance of the given plugin [T] or null if it is not loaded.
 * @see PluginManager.getPlugins
 */
@SparkleDSL
inline fun <reified T : Plugin> instanceOrNull(): T? = pluginManager.plugins.firstOrNull { it is T } as T?

/**
 * This function accesses the plugin instance of the given plugin [T] or throws an exception if it is not loaded.
 * @see PluginManager.getPlugins
 */
@Throws(NoSuchElementException::class)
@SparkleDSL
inline fun <reified T : Plugin> instance(): T = instanceOrNull<T>() ?: throw NoSuchElementException("Plugin ${T::class.simpleName} not found!")

// Key

fun Plugin.key() = Key.key(this.name)


