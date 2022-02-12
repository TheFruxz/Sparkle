package de.jet.paper.app.old_component.essentials

import de.jet.paper.app.old_component.essentials.point.PointInterchange
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

class EssentialsComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Essentials"

	override fun component() {
		interchange(PointInterchange())
		// TODO PRODUCING HUGE DELAY AT REGISTERING measureTime { interchange(WorldInterchange()) }.let { println("2 - $it") }
	}

}