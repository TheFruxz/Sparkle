package de.moltenKt.paper.app.component.keeper

import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent

internal class KeeperComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "iKeeper"

	override suspend fun component() {

		service(KeeperService())

	}

}