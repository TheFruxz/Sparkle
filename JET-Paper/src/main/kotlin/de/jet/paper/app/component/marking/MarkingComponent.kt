package de.jet.paper.app.component.marking

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class MarkingComponent : SmartComponent(system, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Markings"

	override fun component() {

		interchange(MarkingInterchange())

	}

}