package de.moltenKt.paper.tool.smart

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: Identifiable<App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}