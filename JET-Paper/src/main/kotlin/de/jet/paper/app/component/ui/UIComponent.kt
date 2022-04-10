package de.jet.paper.app.component.ui

import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class UIComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "UI"

	override suspend fun component() {

		listener(PanelLinkListener())
		listener(ItemTagListener())
		listener(SplashScreenListener())

		service(SplashScreenService())

	}

}