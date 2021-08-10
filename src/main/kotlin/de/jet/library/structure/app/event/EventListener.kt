package de.jet.library.structure.app.event

import de.jet.library.structure.app.App
import de.jet.library.tool.smart.Identifiable
import org.bukkit.event.Listener
import java.util.*

interface EventListener : Listener, Identifiable<EventListener> {

	val listenerIdentity: String
		get() = this::class.simpleName ?: "${UUID.randomUUID()}"

	val vendor: App

	override val id: String
		get() = listenerIdentity

}