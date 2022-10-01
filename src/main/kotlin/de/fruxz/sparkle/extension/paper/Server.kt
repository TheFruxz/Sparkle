package de.fruxz.sparkle.extension.paper

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandMap
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * This function performs a command sync, which is hidden from the public api access.
 * This helps to validate commands, registered during runtime.
 * @author Fruxz
 * @since 1.0
 */
fun Server.internalSyncCommands() {
	this::class.declaredFunctions.firstOrNull { it.name == "syncCommands" }?.let {
		it.isAccessible = true
		it.call(this)
	}
}

/**
 * This computational value returns the command map used by the server internally.
 * This helps to register a command during runtime.
 * @author Fruxz
 * @since 1.0
 */
val Server.internalCommandMap: CommandMap
	get() {
		this::class.declaredFunctions.firstOrNull { it.name.contains("commandMap", true) }?.let {
			it.isAccessible = true
			return it.call(Bukkit.getServer()) as CommandMap
		}

		throw IllegalStateException("Could not find command map!")
	}