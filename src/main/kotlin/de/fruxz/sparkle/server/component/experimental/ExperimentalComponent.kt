package de.fruxz.sparkle.server.component.experimental

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class ExperimentalComponent : SmartComponent(DISABLED, true) {

	override val label = "ExperimentalFeatures"

	override suspend fun component() {

		interchange(ChangeSkinInterchange())

	}


}