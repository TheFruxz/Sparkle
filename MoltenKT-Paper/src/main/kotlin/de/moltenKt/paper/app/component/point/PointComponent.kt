package de.moltenKt.paper.app.component.point

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class PointComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "World-Points"

	override suspend fun component() {

		interchange(PointInterchange())

	}
}