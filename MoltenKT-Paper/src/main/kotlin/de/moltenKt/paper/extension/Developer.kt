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
import de.moltenKt.paper.tool.smart.VendorOnDemand
import java.util.logging.Level
import kotlin.reflect.full.hasAnnotation

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (MoltenApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(Level.WARNING, "[DEBUG] $line")
		}
	}
}

fun <T : Any?> T.debugLog(messageProcess: T.() -> String) {
	if (MoltenApp.debugMode) { debugLog(messageProcess()) }
}

fun mainLog(level: Level = Level.INFO, message: String) = MoltenApp.instance.log.log(level, message)

val mainLog = MoltenApp.instance.log

internal val lang: MoltenLanguage.MoltenLanguageContainer
	get() = MoltenLanguage.container

internal val system: MoltenApp
	get() = MoltenApp.instance

@Throws(NoSuchElementException::class)
fun app(id: String) = MoltenCache.registeredApps.first { it.appIdentity == id }

@Throws(NoSuchElementException::class)
fun app(vendor: Identifiable<out App>) = MoltenCache.registeredApps.first { it.appIdentity == vendor.identity }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<out App>) = MoltenCache.registeredApps.first { it.appIdentity == vendorIdentity.identity }

@Throws(NoSuchElementException::class)
fun Identifiable<out App>.getApp() = app(identity)

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