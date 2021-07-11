package io.quad.library.extension

import io.quad.app.QuadApp
import java.util.logging.Level

fun debugLog(message: String) {
	if (/*TODO developerMode*/ false) {
		QuadApp.instance.log.log(Level.INFO, message)
	}
}