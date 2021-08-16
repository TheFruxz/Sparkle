package de.jet.library.structure.component

import de.jet.app.JetCache
import de.jet.library.structure.app.App
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Identity
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.smart.VendorsIdentifiable

abstract class Component(
	override val vendor: App,
	val autoEnable: Boolean,
) : VendorsIdentifiable<Component>, Logging {

	override val vendorIdentity: Identity<out App>
		get() = vendor.identityObject

	override val sectionLabel: String
		get() = "component/$identity"

	val isRunning: Boolean
		get() = JetCache.runningComponents.contains(identityObject)

	abstract fun start()

	abstract fun stop()

}