package dev.fruxz.sparkle.framework.extension

import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.annotation.SparkleDSL
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@SparkleDSL fun SparklePlugin(plugin: SparklePlugin.() -> Unit) = object : SparklePlugin(plugin) { }

// Instance access

val pluginManager by lazy { Bukkit.getPluginManager() }

/**
 * This function accesses the plugin instance of the given plugin [T] or null if it doesn't exist.
 */
@SparkleDSL inline fun <reified T : JavaPlugin> instanceOrNull() = pluginManager.plugins.firstOrNull { it is T } as T?

/**
 * This function accesses the plugin instance of the given plugin [T] or throws an exception if it doesn't exist.
 */
@Throws(NoSuchElementException::class)
@SparkleDSL inline fun <reified T : JavaPlugin> instance() = instanceOrNull<T>() ?: throw NoSuchElementException("Plugin ${T::class.simpleName} not found!")


