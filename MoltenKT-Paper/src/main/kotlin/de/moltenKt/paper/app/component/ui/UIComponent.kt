package de.moltenKt.paper.app.component.ui

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class UIComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "UI"

	override suspend fun component() {

		listener(PanelLinkListener())
		listener(ItemTagListener())
		listener(SplashScreenListener())
		listener(CanvasListener())

		service(SplashScreenService())

	}

}