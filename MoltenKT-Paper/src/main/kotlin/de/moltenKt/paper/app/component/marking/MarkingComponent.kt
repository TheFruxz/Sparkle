package de.moltenKt.paper.app.component.marking

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class MarkingComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "Markings"

	override suspend fun component() {

		interchange(MarkingInterchange())

	}

}