package de.fruxz.sparkle.app.component.events

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class EventsComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Events"

	override suspend fun component() {

		listener(EventsListener())

	}

}