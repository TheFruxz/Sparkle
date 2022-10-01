package de.fruxz.sparkle.app.component.completion

import de.fruxz.sparkle.structure.component.SmartComponent

internal class ProtectionComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Protection"

    override suspend fun component() {

        listener(ProtectionListener())

    }

}