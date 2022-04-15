package de.moltenKt.paper.app.component.service

import de.moltenKt.paper.structure.component.Component.RunType.ENABLED
import de.moltenKt.paper.structure.component.SmartComponent

internal class ServiceComponent : SmartComponent(ENABLED) {

	override val thisIdentity = "Service"

	override suspend fun component() {

		interchange(ServiceInterchange())

	}

}