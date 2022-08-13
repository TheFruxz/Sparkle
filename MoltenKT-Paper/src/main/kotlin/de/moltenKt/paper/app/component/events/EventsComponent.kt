package de.moltenKt.paper.app.component.events

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class EventsComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Events"

	override suspend fun component() {

		listener(EventsListener())

	}

}