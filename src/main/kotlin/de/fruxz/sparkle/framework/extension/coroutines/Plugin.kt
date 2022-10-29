package de.fruxz.sparkle.framework.extension.coroutines

import de.fruxz.sparkle.framework.scheduler.PluginCoroutineDispatcher
import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin

fun Plugin.pluginCoroutineDispatcher(isAsync: Boolean = true) = PluginCoroutineDispatcher(this, isAsync)

val Plugin.key: NamespacedKey
	get() = NamespacedKey.minecraft(name.lowercase())