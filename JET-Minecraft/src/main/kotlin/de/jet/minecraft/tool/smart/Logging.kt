package de.jet.minecraft.tool.smart

import de.jet.library.tool.smart.Identifiable
import de.jet.minecraft.structure.app.App
import java.util.logging.Logger

interface Logging {

	val vendor: Identifiable<App>

	val sectionLabel: String

	val sectionLog: Logger
		get() = App.createLog(vendor.identity, sectionLabel)

}