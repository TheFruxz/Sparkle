package de.jet.minecraft.extension

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.minecraft.JET
import de.jet.minecraft.app.JetApp
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.runtime.app.LanguageSpeaker
import de.jet.minecraft.runtime.lang.LanguageData
import de.jet.minecraft.structure.app.App
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (JetApp.debugMode) {
		mainLog(Level.WARNING, "[DEBUG] $message")
	}
}

internal fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log.log(level, message)

internal val lang: LanguageSpeaker
	get() = JET.languageSpeaker

// TODO: 12.10.21 LanguageSpeaker bei Address austauschen mit sinvoller, erst dann existierender Klasse
fun getSystemTranslated(vendor: Identifiable<App>, address: Address<LanguageData>): String {
	return lang(id = address.addressString) //todo replace with real system
}

operator fun LanguageSpeaker.get(id: String) = lang(id)

internal fun lang(id: String, smartColor: Boolean = true) = lang.message(id, smartColor)

internal val system: JetApp
	get() = JET.appInstance 

@Throws(NoSuchElementException::class)
fun app(id: String) = JetCache.registeredApplications.first { it.appIdentity == id }

@Throws(NoSuchElementException::class)
fun app(vendor: Identifiable<App>) = JetCache.registeredApplications.first { it.appIdentity == vendor.identity }

@Throws(NoSuchElementException::class)
fun app(vendorIdentity: Identity<App>) = JetCache.registeredApplications.first { it.appIdentity == vendorIdentity.identity }

@Throws(NoSuchElementException::class)
fun Identifiable<App>.getApp() = app(identity)