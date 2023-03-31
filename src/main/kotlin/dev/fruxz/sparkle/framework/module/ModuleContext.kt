package dev.fruxz.sparkle.framework.module

import org.bukkit.command.CommandExecutor

interface ModuleContext

inline fun <reified T : CommandExecutor> ModuleContext.command(vararg constructorArgs: Any?) { // TODO sparkle commands

    T::class.constructors.firstOrNull()?.call(*constructorArgs) // TODO Check every, if exception, take next

    // This is needed for generalization across all modules

}