package de.jet.paper.extension

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.paper.JET
import de.jet.paper.app.JetApp
import de.jet.paper.app.JetCache
import de.jet.paper.runtime.app.LanguageSpeaker
import de.jet.paper.runtime.lang.LanguageData
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.annotation.AutoRegister
import de.jet.paper.tool.annotation.ExperimentalRegistrationApi
import de.jet.paper.tool.smart.VendorOnDemand
import java.util.logging.Level
import kotlin.reflect.full.hasAnnotation

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (JetApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(Level.WARNING, "[DEBUG] $line")
		}
	}
}

fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log.log(level, message)

val mainLog = JetApp.instance.log

internal val lang: LanguageSpeaker
	get() = JET.languageSpeaker

// TODO: 12.10.21 LanguageSpeaker bei Address austauschen mit sinnvoller, erst dann existierender Klasse
fun getSystemTranslated(@Suppress("UNUSED_PARAMETER") vendor: Identifiable<out App>, address: Address<LanguageData>): String {
	return lang(id = address.addressString) //todo replace with real system
}

operator fun LanguageSpeaker.get(id: String) = lang(id)

internal fun lang(id: String, smartColor: Boolean = true, basicHTML: Boolean = true) = lang.message(id, smartColor, basicHTML)

internal val system: JetApp
	get() = JET.appInstance

@Throws(NoSuchElementException::class)
fun app(id: String) = JetCache.registeredApplications.first { it.appIdentity == id }

@Throws(NoSuchElementException::class)
fun app(vendor: Identifiable<out App>) = JetCache.registeredApplications.first { it.appIdentity == vendor.identity }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<out App>) = JetCache.registeredApplications.first { it.appIdentity == vendorIdentity.identity }

@Throws(NoSuchElementException::class)
fun Identifiable<out App>.getApp() = app(identity)

@Suppress("FINAL_UPPER_BOUND")
@OptIn(ExperimentalRegistrationApi::class)
fun <T : VendorOnDemand> T.runIfAutoRegister() {
	if (this::class.hasAnnotation<AutoRegister>()) {
		if (preferredVendor != null) {

			when (this) {

				is Component -> {
					JetCache.initializationProcesses.add { preferredVendor?.add(this) }
					debugLog(this::class.simpleName + " added to Component-AutoRegister")
				}

				is Interchange -> {
					JetCache.initializationProcesses.add { preferredVendor?.add(this) }
					debugLog(this::class.simpleName + " added to Interchange-AutoRegister")
				}

				is EventListener -> {
					JetCache.initializationProcesses.add { preferredVendor?.add(this) }
					debugLog(this::class.simpleName + " added to EventListener-AutoRegister")
				}

			}

		}
	}
}