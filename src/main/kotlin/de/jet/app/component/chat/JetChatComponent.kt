package de.jet.app.component.chat

import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component

class JetChatComponent(vendor: App) : Component(vendor) {

	override val id = "JETChat"

	val eventListeners = mutableListOf<EventListener>()

	override fun start() {

	}

	override fun stop() {

	}

}