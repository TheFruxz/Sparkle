package de.fruxz.sparkle.server.component.sandbox

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class SandBoxComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Sandboxing"

	override suspend fun component() {

		interchange(SandBoxInterchange())

	}

}