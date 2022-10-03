package de.fruxz.sparkle.server.component.events

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class EventsComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Events"

	override suspend fun component() {

		listener(EventsListener())

	}

}