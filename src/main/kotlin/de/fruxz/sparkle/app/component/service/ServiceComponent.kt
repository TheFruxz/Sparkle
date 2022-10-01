package de.fruxz.sparkle.app.component.service

import de.fruxz.sparkle.structure.component.Component.RunType.ENABLED
import de.fruxz.sparkle.structure.component.SmartComponent

internal class ServiceComponent : SmartComponent(ENABLED) {

	override val label = "Service"

	override suspend fun component() {

		interchange(ServiceInterchange())

	}

}