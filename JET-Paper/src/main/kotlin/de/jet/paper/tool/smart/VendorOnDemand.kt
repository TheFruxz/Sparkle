package de.jet.paper.tool.smart

import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.annotation.AutoRegister
import de.jet.paper.tool.annotation.ExperimentalRegistrationApi
import kotlin.reflect.full.hasAnnotation

interface VendorOnDemand {

	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

	@Suppress("FINAL_UPPER_BOUND")
	@OptIn(ExperimentalRegistrationApi::class)
	fun <T : AutoRegister> runIfAutoRegister(any: T) {
		if (any::class.hasAnnotation<AutoRegister>() && any is VendorOnDemand) {
			if (preferredVendor != null) {

				when (any) {

					is Component -> {
						preferredVendor?.add(any)
					}

					is Interchange -> {
						preferredVendor?.add(any)
					}

					is EventListener -> {
						preferredVendor?.add(any)
					}

				}

			}
		}
	}

}