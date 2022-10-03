package de.fruxz.sparkle.framework.util.attachment

import de.fruxz.sparkle.framework.infrastructure.app.App

interface VendorOnDemand {

	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

}