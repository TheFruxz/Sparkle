package dev.fruxz.sparkle.framework

import org.bukkit.command.CommandExecutor

interface ModuleContext

inline fun <reified T : CommandExecutor> ModuleContext.command(vararg constructorArgs: Any?) { // TODO sparkle commands, not bukkit!

    T::class.constructors.firstOrNull()?.call(*constructorArgs) // TODO Check every, if exception, take next

}