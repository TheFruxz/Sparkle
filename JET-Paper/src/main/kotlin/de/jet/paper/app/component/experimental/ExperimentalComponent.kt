package de.jet.paper.app.component.experimental

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.DISABLED
import de.jet.paper.structure.component.SmartComponent

internal class ExperimentalComponent : SmartComponent(system, DISABLED) {

	override val thisIdentity = "ExperimentalFeatures"

	override fun component() {

		interchange(ExperimentalChangeSkinInterchange())

	}


}