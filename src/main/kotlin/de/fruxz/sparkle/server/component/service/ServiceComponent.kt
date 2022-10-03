package de.fruxz.sparkle.server.component.service

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.ENABLED
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class ServiceComponent : SmartComponent(ENABLED) {

	override val label = "Service"

	override suspend fun component() {

		interchange(ServiceInterchange())

	}

}