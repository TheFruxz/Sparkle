package de.moltenKt.paper.app.component.buildMode

import de.moltenKt.paper.structure.component.SmartComponent

internal class BuildModeComponent : SmartComponent() {

	override val thisIdentity = "BuildMode"

	override suspend fun component() {
		listener(BuildModeListener())
		interchange(BuildModeInterchange())
	}

}