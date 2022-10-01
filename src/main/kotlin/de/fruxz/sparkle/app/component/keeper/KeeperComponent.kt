package de.fruxz.sparkle.app.component.keeper

import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent

internal class KeeperComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "iKeeper"

	override suspend fun component() {

		service(KeeperService())

	}

}