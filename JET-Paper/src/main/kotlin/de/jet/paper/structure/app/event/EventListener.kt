package de.jet.paper.structure.app.event

import de.jet.paper.structure.app.App
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.tool.smart.VendorsIdentifiable
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