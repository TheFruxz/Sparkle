package de.jet.paper.app.component.service

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.ENABLED
import de.jet.paper.structure.component.SmartComponent

internal class ServiceComponent : SmartComponent(system, ENABLED) {

	override val thisIdentity = "Service"

	override fun component() {

		interchange(ServiceInterchange())

	}

}