package de.moltenKt.paper.app.component.experimental

import de.moltenKt.paper.structure.component.Component.RunType.DISABLED
import de.moltenKt.paper.structure.component.SmartComponent

internal class ExperimentalComponent : SmartComponent(DISABLED, true) {

	override val label = "ExperimentalFeatures"

	override suspend fun component() {

		interchange(ChangeSkinInterchange())

	}


}