package de.fruxz.sparkle.server.component.completion

import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class ProtectionComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Protection"

    override suspend fun component() {

        listener(ProtectionListener())

    }

}