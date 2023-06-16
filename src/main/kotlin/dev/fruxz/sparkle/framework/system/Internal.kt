package dev.fruxz.sparkle.framework.system

import org.bukkit.Server
import org.jetbrains.annotations.ApiStatus
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * It is dangerous to use this api, because it depends on a function
 * inside the code of the paper server, which is currently under
 * affected by heavy changes, like the brigadier support.
 * @author Fruxz
 * @since 2023.1
 */
@ApiStatus.Internal
fun Server.internalSyncCommands() {
    this::class.declaredFunctions.firstOrNull { it.name == "syncCommands" }?.let {
        it.isAccessible = true
        it.call(this)
    }
}