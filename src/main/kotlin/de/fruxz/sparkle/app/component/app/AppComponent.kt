package de.fruxz.sparkle.app.component.app

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_IMMUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class AppComponent : SmartComponent(AUTOSTART_IMMUTABLE) {

	override val label = "AppManagement"

	override suspend fun component() {

		interchange(AppInterchange())

	}

}