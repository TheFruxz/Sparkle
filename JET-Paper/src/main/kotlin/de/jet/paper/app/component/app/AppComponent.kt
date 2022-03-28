package de.jet.paper.app.component.app

import de.jet.paper.structure.component.Component.RunType.AUTOSTART_IMMUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class AppComponent : SmartComponent(AUTOSTART_IMMUTABLE) {

	override val thisIdentity = "AppManagement"

	override suspend fun component() {
		TODO("Not yet implemented")
	}

}