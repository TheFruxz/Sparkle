package de.jet.library.tool.smart

import de.jet.library.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: Identifiable<App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}