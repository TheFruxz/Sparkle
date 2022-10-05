package de.fruxz.sparkle.framework.extension.coroutines

import de.fruxz.sparkle.framework.scheduler.PluginCoroutineDispatcher
import org.bukkit.plugin.Plugin

fun Plugin.pluginCoroutineDispatcher(isAsync: Boolean = true) = PluginCoroutineDispatcher(this, isAsync)