package de.fruxz.sparkle.framework.extension.coroutines

import kotlinx.coroutines.Dispatchers
import org.bukkit.plugin.Plugin

fun Dispatchers.plugin(plugin: Plugin, isAsync: Boolean = true) = plugin.pluginCoroutineDispatcher(isAsync)