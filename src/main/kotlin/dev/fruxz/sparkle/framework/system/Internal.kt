package dev.fruxz.sparkle.framework.system

import org.bukkit.Server
import org.jetbrains.annotations.ApiStatus
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

fun Server.internalSyncCommands() {
    this::class.declaredFunctions.firstOrNull { it.name == "syncCommands" }?.let {
        it.isAccessible = true
        it.call(this)
    }
}