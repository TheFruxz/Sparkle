package de.jet.paper.app.component.linking

import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class ContainerLinkComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "ItemLink"

	override suspend fun component() {

		listener(ItemLinkListener())
		listener(PanelLinkListener())
		listener(ItemTagListener())

	}

}