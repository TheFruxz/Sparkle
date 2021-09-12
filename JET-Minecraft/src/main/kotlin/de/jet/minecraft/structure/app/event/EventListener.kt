package de.jet.minecraft.structure.app.event

import de.jet.minecraft.structure.app.App
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Identity
import de.jet.minecraft.tool.smart.VendorsIdentifiable
import org.bukkit.event.Listener
import java.util.*

interface EventListener : Listener, VendorsIdentifiable<EventListener> {

	val vendor: App

	val listenerIdentity: String
		get() = this::class.simpleName ?: "${UUID.randomUUID()}"

	override val vendorIdentity: Identity<App>
		get() = vendor.identityObject

	override val thisIdentity: String
		get() = listenerIdentity

}