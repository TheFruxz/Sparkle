package de.jet.library.structure.app.event

import de.jet.library.structure.app.App
import org.bukkit.event.Listener
import java.util.*

interface EventListener : Listener {

	val listenerIdentity: String
		get() = this::class.simpleName ?: "${UUID.randomUUID()}"

	val vendor: App

}