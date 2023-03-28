package dev.fruxz.sparkle.framework.extension

import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.annotation.SparkleDSL
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

@SparkleDSL fun SparklePlugin(plugin: SparklePlugin.() -> Unit) = object : SparklePlugin(plugin) { }

// Instance access

/**
 * This function accesses the plugin instance of the given plugin [T] or null if it doesn't exist.
 */
@SparkleDSL inline fun <reified T : JavaPlugin> instanceOrNull() = Bukkit.getPluginManager().plugins.firstOrNull { it is T } as T?

/**
 * This function accesses the plugin instance of the given plugin [T] or throws an exception if it doesn't exist.
 */
@Throws(NoSuchElementException::class)
@SparkleDSL inline fun <reified T : JavaPlugin> instance() = instanceOrNull<T>() ?: throw NoSuchElementException("Plugin ${T::class.simpleName} not found!")

// Scopes

private val pluginScopes = mutableMapOf<KClass<out JavaPlugin>, CoroutineScope>()

fun pluginCoroutineScope(plugin: KClass<out JavaPlugin>): CoroutineScope {
    val current = pluginScopes[plugin]

    return if (current == null) {

        if ((plugin == LocalSparklePlugin::class) || Bukkit.getPluginManager().getPlugin(LocalSparklePlugin.SYSTEM_IDENTITY) == null) {
            CoroutineScope(SupervisorJob() + Dispatchers.Default) // If this is the main-plugin OR sparkle is not enabled
        } else
            CoroutineScope(SupervisorJob() + pluginCoroutineScope<LocalSparklePlugin>().coroutineContext) // If this is a sub-plugin

    } else current
}

@SparkleDSL inline fun <reified T : JavaPlugin> pluginCoroutineScope() = pluginCoroutineScope(T::class)

/**
 * This function accesses the custom coroutine scope of the given plugin.
 * Used in the *this* context.
 */
@SparkleDSL fun JavaPlugin.coroutineScope() = pluginCoroutineScope(this::class)

