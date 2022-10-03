package de.fruxz.sparkle.framework.util.extension.coroutines

import de.fruxz.sparkle.framework.util.scheduler.PluginCoroutineDispatcher
import org.bukkit.plugin.Plugin

fun Plugin.pluginCoroutineDispatcher(isAsync: Boolean = true) = PluginCoroutineDispatcher(this, isAsync)