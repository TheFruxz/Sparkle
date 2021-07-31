package de.jet.library.structure.smart

import de.jet.library.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: Identifiable<App>

	val logSection: String

	val log: Logger
		get() = App.createLog(vendor.id, logSection)

}