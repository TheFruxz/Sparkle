package de.jet.library.extension

import de.jet.app.JetApp
import de.jet.library.JET
import de.jet.library.runtime.app.LanguageSpeaker
import kotlinx.serialization.Serializable
import java.util.logging.Level

fun <T : Any?> T.debugLog(message: String) = this.also {
	if (/*TODO developerMode*/ false) {
		mainLog(Level.INFO, message)
	}
}

internal fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log.log(level, message)

internal val lang: LanguageSpeaker
	get() = JET.languageSpeaker

internal fun lang(id: String, smartColor: Boolean = true) = lang.message(id)

val Any.asString: String
	get() = toString()