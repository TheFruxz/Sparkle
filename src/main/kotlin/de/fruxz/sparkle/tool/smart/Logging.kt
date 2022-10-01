package de.fruxz.sparkle.tool.smart

import de.fruxz.sparkle.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: KeyedIdentifiable<out App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}