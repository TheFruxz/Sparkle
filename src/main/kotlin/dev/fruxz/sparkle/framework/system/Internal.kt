package dev.fruxz.sparkle.framework.system

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandMap
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

fun Server.internalSyncCommands() {
    this::class.declaredFunctions.firstOrNull { it.name == "syncCommands" }?.let {
        it.isAccessible = true
        it.call(this)
    }
}

val Server.internalCommandMap: CommandMap
    get() {
        this::class.declaredFunctions.firstOrNull { it.name.contains("commandMap", true) }?.let {
            it.isAccessible = true
            return it.call(Bukkit.getServer()) as CommandMap
        }

        throw IllegalStateException("Could not find commandMap!")
    }