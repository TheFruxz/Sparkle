package de.moltenKt.paper.extension

import de.moltenKt.core.extension.dump
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.MoltenLanguage
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.annotation.AutoRegister
import de.moltenKt.paper.tool.annotation.ExperimentalRegistrationApi
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.paper.tool.smart.VendorOnDemand
import java.util.logging.Level
import kotlin.reflect.full.hasAnnotation

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = this.also {
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

internal val lang: MoltenLanguage.MoltenLanguageContainer
	get() = MoltenLanguage.container

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

@OptIn(ExperimentalRegistrationApi::class)
fun <T : VendorOnDemand> T.runIfAutoRegister() {
	if (this::class.hasAnnotation<AutoRegister>()) {
		if (preferredVendor != null) {

			when (this) {

				is Component -> {
					MoltenCache.initializationProcesses += { preferredVendor?.add(this).dump() }
					debugLog(this::class.simpleName + " added to Component-AutoRegister")
				}

				is Interchange -> {
					MoltenCache.initializationProcesses += { preferredVendor?.add(this).dump() }
					debugLog(this::class.simpleName + " added to Interchange-AutoRegister")
				}

				is EventListener -> {
					MoltenCache.initializationProcesses += { preferredVendor?.add(this).dump() }
					debugLog(this::class.simpleName + " added to EventListener-AutoRegister")
				}

			}

		}
	}
}