package de.moltenKt.paper.extension

import de.fruxz.ascend.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = also {
	if (MoltenApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(level, "[DEBUG] $line")
		}
	}
}

fun <T : Any?> T.debugLog(level: Level = Level.WARNING, messageProcess: T.() -> String) {
	if (MoltenApp.debugMode) { debugLog(messageProcess(), level) }
}

fun mainLog(level: Level = Level.INFO, message: String) = MoltenApp.instance.log.log(level, message)

val mainLog = MoltenApp.instance.log

internal val system: MoltenApp
	get() = MoltenApp.instance

@Throws(NoSuchElementException::class)
fun app(appIdentity: String) = MoltenCache.registeredApps.first { it.appIdentity == appIdentity }

@Throws(NoSuchElementException::class)
fun app(vendor: KeyedIdentifiable<out App>) = MoltenCache.registeredApps.first { it.key() == vendor.key() }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<out App>) = MoltenCache.registeredApps.first { it.identityObject == vendorIdentity }

@Throws(NoSuchElementException::class)
fun KeyedIdentifiable<out App>.getApp() = app(this)
