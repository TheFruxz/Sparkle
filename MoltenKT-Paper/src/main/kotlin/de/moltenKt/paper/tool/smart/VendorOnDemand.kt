package de.moltenKt.paper.tool.smart

import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.annotation.AutoRegister
import de.moltenKt.paper.tool.annotation.ExperimentalRegistrationApi
import kotlin.reflect.full.hasAnnotation

interface VendorOnDemand {

	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

}