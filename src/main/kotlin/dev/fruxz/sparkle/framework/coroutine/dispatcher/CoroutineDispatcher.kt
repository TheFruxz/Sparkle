package dev.fruxz.sparkle.framework.coroutine.dispatcher

import org.bukkit.plugin.Plugin

fun Plugin.pluginCoroutineDispatcher(isAsync: Boolean = true) =
    PluginCoroutineDispatcher(this, isAsync)

val Plugin.syncDispatcher
    get() = PluginCoroutineDispatcherManager.generateDispatchers(this).first

val Plugin.asyncDispatcher
    get() = PluginCoroutineDispatcherManager.generateDispatchers(this).second

object PluginCoroutineDispatcherManager {

    private val dispatcher = mutableMapOf<Plugin, Pair<PluginCoroutineDispatcher, PluginCoroutineDispatcher>>()

    fun generateDispatchers(plugin: Plugin) = dispatcher.getOrPut(plugin) {
        Pair(plugin.pluginCoroutineDispatcher(false), plugin.pluginCoroutineDispatcher(true))
    }

}