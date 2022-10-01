package de.moltenKt.paper.tool.smart

import de.moltenKt.paper.structure.app.App

interface VendorOnDemand {

	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

}