package de.fruxz.sparkle.app.component.sandbox

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class SandBoxComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Sandboxing"

	override suspend fun component() {

		interchange(SandBoxInterchange())

	}

}