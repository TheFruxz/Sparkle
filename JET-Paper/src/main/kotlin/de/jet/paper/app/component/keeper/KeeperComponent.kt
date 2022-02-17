package de.jet.paper.app.component.keeper

import de.jet.paper.extension.system
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

internal class KeeperComponent : SmartComponent(system, AUTOSTART_MUTABLE) {

	override val thisIdentity = "iKeeper"

	override fun component() {

		service(KeeperService())

	}

}