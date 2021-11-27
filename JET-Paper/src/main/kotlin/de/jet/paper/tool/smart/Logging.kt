package de.jet.paper.tool.smart

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: Identifiable<App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}