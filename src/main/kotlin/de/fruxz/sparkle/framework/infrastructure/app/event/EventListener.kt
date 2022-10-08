package de.fruxz.sparkle.framework.infrastructure.app.event

import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.attachment.VendorOnDemand
import de.fruxz.sparkle.framework.identification.VendorsIdentifiable
import de.fruxz.sparkle.framework.infrastructure.app.App
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