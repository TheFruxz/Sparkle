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
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (JetApp.debugMode) {
		message.lines().forEach { line ->
			mainLog(Level.WARNING, "[DEBUG] $line")
		}
	}
}

internal fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log.log(level, message)

internal val lang: LanguageSpeaker
	get() = JET.languageSpeaker

// TODO: 12.10.21 LanguageSpeaker bei Address austauschen mit sinvoller, erst dann existierender Klasse
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