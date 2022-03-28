package de.jet.paper.app.component.completion

import de.jet.paper.structure.component.SmartComponent

internal class ProtectionComponent : SmartComponent(RunType.AUTOSTART_MUTABLE, true) {

    override val thisIdentity = "Protection"

    override suspend fun component() {

        listener(ProtectionListener())

    }

}