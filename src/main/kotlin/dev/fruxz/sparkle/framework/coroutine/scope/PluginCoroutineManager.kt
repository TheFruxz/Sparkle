package dev.fruxz.sparkle.framework.coroutine.scope

import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.plugin.pluginOrNull
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.plugin.Plugin
import kotlin.reflect.KClass

object PluginCoroutineManager {

    private val pluginScopes = mutableMapOf<KClass<out Plugin>, CoroutineScope>()

    fun pluginCoroutineScope(plugin: KClass<out Plugin>): CoroutineScope {

        return pluginScopes.getOrPut(plugin) {
            when {
                plugin == LocalSparklePlugin::class || pluginOrNull(LocalSparklePlugin.SYSTEM_IDENTITY) == null -> { // If this is the main-plugin OR sparkle is not enabled
                    CoroutineScope(SupervisorJob() + Dispatchers.Default)
                }
                else -> { // If this is a sub-plugin
                    CoroutineScope(SupervisorJob() + pluginCoroutineScope<LocalSparklePlugin>().coroutineContext)
                }
            }
        }
    }

}



@SparkleDSL
inline fun <reified T : Plugin> pluginCoroutineScope() = PluginCoroutineManager.pluginCoroutineScope(T::class)

/**
 * This computational value accesses the custom coroutine scope of the given plugin.
 * Used in the *this* context.
 */
@SparkleDSL
val Plugin.coroutineScope
    get() = PluginCoroutineManager.pluginCoroutineScope(this::class)