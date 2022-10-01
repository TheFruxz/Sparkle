package de.fruxz.sparkle.app.component.experimental

import de.fruxz.sparkle.structure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.structure.component.SmartComponent

internal class ExperimentalComponent : SmartComponent(DISABLED, true) {

	override val label = "ExperimentalFeatures"

	override suspend fun component() {

		interchange(ChangeSkinInterchange())

	}


}