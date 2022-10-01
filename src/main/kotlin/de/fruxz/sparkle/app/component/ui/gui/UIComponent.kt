package de.fruxz.sparkle.app.component.ui.gui

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class UIComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "UserExperience"

	override suspend fun component() {

		listener(ItemTagListener())
		listener(CanvasListener())

	}

}