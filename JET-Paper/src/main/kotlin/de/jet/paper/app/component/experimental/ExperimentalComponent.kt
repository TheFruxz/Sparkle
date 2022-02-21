package de.jet.paper.app.component.experimental

import de.jet.paper.structure.component.Component.RunType.DISABLED
import de.jet.paper.structure.component.SmartComponent

internal class ExperimentalComponent : SmartComponent(DISABLED, true) {

	override val thisIdentity = "ExperimentalFeatures"

	override suspend fun component() {

		interchange(ChangeSkinInterchange())

	}


}