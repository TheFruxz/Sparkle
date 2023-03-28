package dev.fruxz.sparkle.framework.extension.coroutine

import dev.fruxz.sparkle.framework.coroutine.PluginCoroutineDispatcher
import org.bukkit.plugin.Plugin

fun Plugin.pluginCoroutineDispatcher(isAsync: Boolean = true) = PluginCoroutineDispatcher(this, isAsync)

private val dispatcher = mutableMapOf<Plugin, Pair<PluginCoroutineDispatcher, PluginCoroutineDispatcher>>()

val Plugin.syncDispatcher
    get() = dispatcher.getOrPut(this) {
        Pair(pluginCoroutineDispatcher(false), pluginCoroutineDispatcher(true))
    }.first

val Plugin.asyncDispatcher
    get() = dispatcher.getOrPut(this) {
        Pair(pluginCoroutineDispatcher(false), pluginCoroutineDispatcher(true))
    }.second