package de.jet.paper.structure.app.event

import de.jet.jvm.extension.tryOrNull
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.structure.app.App
import de.jet.paper.tool.smart.VendorOnDemand
import de.jet.paper.tool.smart.VendorsIdentifiable
import org.bukkit.event.Listener
import java.util.*

abstract class EventListener(
	final override val preferredVendor: App? = null
) : Listener, VendorsIdentifiable<EventListener>, VendorOnDemand {

	init {

		preferredVendor?.let {
			vendor = it
		}

	}

	override fun replaceVendor(newVendor: App, override: Boolean) = if (override || !this::vendor.isInitialized) {
		vendor = newVendor
		true
	} else
		false

	lateinit var vendor: App
		internal set

	val isVendorCurrentlySet: Boolean
		get() = this::vendor.isInitialized

	val listenerIdentity: String
		get() = tryOrNull { this::class.simpleName } ?: "${UUID.randomUUID()}"

	override val vendorIdentity: Identity<App>
		get() = vendor.identityObject

	override val thisIdentity: String
		get() = listenerIdentity

}