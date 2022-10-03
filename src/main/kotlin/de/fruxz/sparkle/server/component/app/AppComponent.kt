package de.fruxz.sparkle.server.component.app

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_IMMUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class AppComponent : SmartComponent(AUTOSTART_IMMUTABLE) {

	override val label = "AppManagement"

	override suspend fun component() {

		interchange(AppInterchange())

	}

}