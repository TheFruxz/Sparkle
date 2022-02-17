package de.jet.paper.app.component.buildMode

import de.jet.paper.extension.system
import de.jet.paper.structure.component.SmartComponent

internal class BuildModeComponent : SmartComponent(system) {

	override val thisIdentity = "BuildMode"

	override suspend fun component() {
		listener(BuildModeListener())
		interchange(BuildModeInterchange())
	}

}