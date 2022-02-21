package de.jet.paper.app.component.events

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class EventsComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "Events"

	override suspend fun component() {

		listener(EventsListener())

	}

}