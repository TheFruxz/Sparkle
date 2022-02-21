package de.jet.paper.app.component.sandbox

import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class SandBoxComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "Sandboxing"

	override suspend fun component() {

		interchange(SandBoxInterchange())

	}

}