package de.fruxz.sparkle.framework.attachment

import de.fruxz.sparkle.framework.infrastructure.app.App

interface VendorOnDemand {

	/**
	 * The class, which implements this [VendorOnDemand] interfaces
	 * selects the [App] on runtime. This property is here, if you
	 * want to 'force' the class, to take your [App] instead of the
	 * automatically selected one.
	 */
	val preferredVendor: App?

	fun replaceVendor(newVendor: App, override: Boolean = false): Boolean

}