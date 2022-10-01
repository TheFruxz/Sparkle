package de.fruxz.sparkle.tool.smart

import de.fruxz.sparkle.structure.app.App

interface VendorOnDemand {

	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

}