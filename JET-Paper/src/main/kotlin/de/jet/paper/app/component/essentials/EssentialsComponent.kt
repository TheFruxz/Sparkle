package de.jet.paper.app.component.essentials

import de.jet.paper.app.component.essentials.point.PointInterchange
import de.jet.paper.app.component.essentials.world.WorldInterchange
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

class EssentialsComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Essentials"

	override fun component() {
		interchange(PointInterchange(vendor))
		interchange(WorldInterchange(vendor))
	}

}