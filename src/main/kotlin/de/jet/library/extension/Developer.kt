package de.jet.library.extension

import de.jet.app.JetApp
import java.util.logging.Level

fun debugLog(message: String) {
	if (/*TODO developerMode*/ false) {
		JetApp.instance.log.log(Level.INFO, message)
	}
}