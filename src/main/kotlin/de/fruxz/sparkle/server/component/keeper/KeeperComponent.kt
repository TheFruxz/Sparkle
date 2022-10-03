package de.fruxz.sparkle.server.component.keeper

import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class KeeperComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "iKeeper"

	override suspend fun component() {

		service(KeeperService())

	}

}