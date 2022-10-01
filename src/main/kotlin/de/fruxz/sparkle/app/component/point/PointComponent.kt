package de.fruxz.sparkle.app.component.point

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class PointComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "World-Points"

	override suspend fun component() {

		interchange(PointInterchange())

	}
}