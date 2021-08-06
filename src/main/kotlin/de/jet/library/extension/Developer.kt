package de.jet.library.extension

import de.jet.app.JetApp
import de.jet.app.JetCache
import de.jet.library.JET
import de.jet.library.runtime.app.LanguageSpeaker
import de.jet.library.structure.app.App
import de.jet.library.structure.smart.Identifiable
import kotlinx.serialization.Serializable
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (/*TODO developerMode*/ false) {
		mainLog(Level.INFO, message)
	}
}

fun debugLog(message: String) = "".debugLog(message)

internal fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log.log(level, message)

internal val lang: LanguageSpeaker
	get() = JET.languageSpeaker

internal fun lang(id: String, smartColor: Boolean = true) = lang.message(id, smartColor)

val Any.asString: String
	get() = toString()

fun <T> checkAllObjects(vararg objects: T, check: T.() -> Boolean): Boolean {
	return objects.all(check)
}

@Throws(NoSuchElementException::class)
fun app(id: String) = JetCache.registeredApplications.first { it.appIdentity.equals(it) }

@Throws(NoSuchElementException::class)
fun Identifiable<App>.getApp() = app(id)