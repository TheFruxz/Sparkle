package de.moltenKt.paper.app.component.app

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_IMMUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class AppComponent : SmartComponent(AUTOSTART_IMMUTABLE) {

	override val label = "AppManagement"

	override suspend fun component() {

		interchange(AppInterchange())

	}

}