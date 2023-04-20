package dev.fruxz.sparkle.framework.coroutine.scope

import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

private val pluginScopes = mutableMapOf<KClass<out JavaPlugin>, CoroutineScope>()

fun pluginCoroutineScope(plugin: KClass<out Plugin>): CoroutineScope {

    return pluginScopes[plugin]
        ?: when {
            plugin == LocalSparklePlugin::class || Bukkit.getPluginManager()
                .getPlugin(LocalSparklePlugin.SYSTEM_IDENTITY) == null -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Default) // If this is the main-plugin OR sparkle is not enabled
            }
            else -> CoroutineScope(SupervisorJob() + pluginCoroutineScope<LocalSparklePlugin>().coroutineContext)
        } // If this is a sub-plugin
}

@SparkleDSL
inline fun <reified T : Plugin> pluginCoroutineScope() = pluginCoroutineScope(T::class)

/**
 * This computational value accesses the custom coroutine scope of the given plugin.
 * Used in the *this* context.
 */
@SparkleDSL
val Plugin.coroutineScope
    get() = pluginCoroutineScope(this::class)