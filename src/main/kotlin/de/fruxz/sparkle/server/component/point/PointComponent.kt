package de.fruxz.sparkle.server.component.point

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class PointComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "World-Points"

	override suspend fun component() {

		interchange(PointInterchange())

	}
}