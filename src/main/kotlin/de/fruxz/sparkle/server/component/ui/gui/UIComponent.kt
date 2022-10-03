package de.fruxz.sparkle.server.component.ui.gui

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class UIComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "UserExperience"

	override suspend fun component() {

		listener(ItemTagListener())
		listener(CanvasListener())

	}

}