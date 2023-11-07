package de.fruxz.sparkle.framework.infrastructure.app.event

import dev.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.attachment.VendorOnDemand
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.Attachable
import de.fruxz.sparkle.framework.infrastructure.app.App
import dev.fruxz.stacked.extension.KeyingStrategy
import dev.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import org.bukkit.event.Listener
import java.util.*

abstract class EventListener(
	final override val preferredVendor: App? = null
) : Attachable, Listener, KeyedIdentifiable<EventListener>, VendorOnDemand {

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

	val listenerIdentity: String by lazy {
		tryOrNull { this::class.simpleName } ?: "${UUID.randomUUID()}"
	}

	override val identityKey: Key by lazy {
		vendor.subKey(listenerIdentity, KeyingStrategy.CONTINUE)
	}

}