package de.fruxz.sparkle.extension

import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.app.MoltenApp
import de.fruxz.sparkle.app.MoltenCache
import de.fruxz.sparkle.structure.app.App
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = also {
	if (de.fruxz.sparkle.app.MoltenApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(level, "[DEBUG] $line")
		}
	}
}

fun <T : Any?> T.debugLog(level: Level = Level.WARNING, messageProcess: T.() -> String) {
	if (de.fruxz.sparkle.app.MoltenApp.debugMode) { debugLog(messageProcess(), level) }
}

fun mainLog(level: Level = Level.INFO, message: String) = de.fruxz.sparkle.app.MoltenApp.instance.log.log(level, message)

val mainLog = de.fruxz.sparkle.app.MoltenApp.instance.log

internal val system: de.fruxz.sparkle.app.MoltenApp
	get() = de.fruxz.sparkle.app.MoltenApp.instance

@Throws(NoSuchElementException::class)
fun app(appIdentity: String) = MoltenCache.registeredApps.first { it.appIdentity == appIdentity }

@Throws(NoSuchElementException::class)
fun app(vendor: KeyedIdentifiable<out App>) = MoltenCache.registeredApps.first { it.key() == vendor.key() }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<out App>) = MoltenCache.registeredApps.first { it.identityObject == vendorIdentity }

@Throws(NoSuchElementException::class)
fun KeyedIdentifiable<out App>.getApp() = app(this)
