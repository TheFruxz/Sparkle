package de.jet.minecraft.structure.app.event

import de.jet.minecraft.structure.app.App
import de.jet.library.tool.smart.Identifiable
import org.bukkit.event.Listener
import java.util.*

interface EventListener : Listener, Identifiable<EventListener> {

	val listenerIdentity: String
		get() = this::class.simpleName ?: "${UUID.randomUUID()}"

	val vendor: App

	override val identity: String
		get() = listenerIdentity

}