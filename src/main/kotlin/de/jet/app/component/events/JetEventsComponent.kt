package de.jet.app.component.events

import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import java.util.logging.Level

class JetEventsComponent(vendor: App) : Component(vendor) {

	override val id = "JETEvents"

	val eventListeners = mutableListOf<EventListener>()

	override fun start() {
		log.log(Level.WARNING, "WOW! We did it, is this true?")
	}

	override fun stop() {
		log.log(Level.WARNING, "Yeah it is!")
	}
}