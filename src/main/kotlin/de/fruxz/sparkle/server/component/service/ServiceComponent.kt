package de.fruxz.sparkle.server.component.service

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.DISABLED
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class ServiceComponent : SmartComponent(DISABLED) { // TODO disabled is debug

	override val label = "Service"

	override suspend fun component() {

		interchange(ServiceInterchange())

	}

}