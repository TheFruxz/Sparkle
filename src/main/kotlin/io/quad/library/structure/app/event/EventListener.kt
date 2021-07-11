package io.quad.library.structure.app.event

import io.quad.library.structure.app.App
import org.bukkit.event.Listener
import java.util.*

interface EventListener : Listener {

	val listenerIdentity: String
		get() = this::class.simpleName ?: "${UUID.randomUUID()}"

	val host: App

}