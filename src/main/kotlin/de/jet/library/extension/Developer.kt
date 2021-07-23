package de.jet.library.extension

import de.jet.app.JetApp
import kotlinx.serialization.Serializable
import java.util.logging.Level

fun debugLog(message: String) {
	if (/*TODO developerMode*/ false) {
		mainLog(Level.INFO, message)
	}
}

internal fun mainLog(level: Level = Level.INFO, message: String) = JetApp.instance.log?.log(level, message)