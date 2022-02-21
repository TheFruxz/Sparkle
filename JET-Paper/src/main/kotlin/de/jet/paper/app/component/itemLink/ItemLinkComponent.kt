package de.jet.paper.app.component.itemLink

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class ItemLinkComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val thisIdentity = "ItemLink"

	override suspend fun component() {

		listener(ItemLinkListener())

	}

}