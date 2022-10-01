package de.fruxz.sparkle.app.component.buildMode

import de.fruxz.sparkle.structure.component.SmartComponent

internal class BuildModeComponent : SmartComponent() {

	override val label = "BuildMode"

	override suspend fun component() {
		listener(BuildModeListener())
		interchange(BuildModeInterchange())
	}

}