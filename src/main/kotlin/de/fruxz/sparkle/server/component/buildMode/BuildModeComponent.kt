package de.fruxz.sparkle.server.component.buildMode

import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class BuildModeComponent : SmartComponent() {

	override val label = "BuildMode"

	override suspend fun component() {
		listener(BuildModeListener())
		interchange(BuildModeInterchange())
	}

}