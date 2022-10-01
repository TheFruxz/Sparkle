package de.moltenKt.paper.app.component.completion

import de.moltenKt.paper.structure.component.SmartComponent

internal class ProtectionComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val label = "Protection"

    override suspend fun component() {

        listener(ProtectionListener())

    }

}