package de.fruxz.sparkle.framework.extension

import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleCache
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = also {
	if (SparkleApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(level, "[DEBUG] $line")
		}
	}
}

fun <T : Any?> T.debugLog(level: Level = Level.WARNING, messageProcess: T.() -> String) {
	if (SparkleApp.debugMode) { debugLog(tryOrNull { messageProcess() } ?: ">> Error at getting this debugLog message!", level) }
}

fun mainLog(level: Level = Level.INFO, message: String) = SparkleApp.instance.log.log(level, message)

val mainLog = SparkleApp.instance.log

val sparkle: SparkleApp
	get() = SparkleApp.instance

val apps: Set<App>
	get() = SparkleCache.registeredApps

@Throws(NoSuchElementException::class)
fun app(appIdentity: String) = apps.first { it.label == appIdentity }

@Throws(NoSuchElementException::class)
fun app(vendor: KeyedIdentifiable<out App>) = apps.first { it.key() == vendor.key() }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<out App>) = apps.first { it.identityObject == vendorIdentity }

@Throws(NoSuchElementException::class)
fun KeyedIdentifiable<out App>.getApp() = app(this)
