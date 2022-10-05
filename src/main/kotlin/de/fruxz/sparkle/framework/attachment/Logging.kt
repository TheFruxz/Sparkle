package de.fruxz.sparkle.framework.attachment

import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import java.util.logging.Logger

interface Logging {

	val vendor: KeyedIdentifiable<out App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}