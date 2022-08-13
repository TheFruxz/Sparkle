package de.moltenKt.paper.app.component.component

import de.moltenKt.paper.structure.component.SmartComponent

internal class ComponentComponent : SmartComponent(RunType.ENABLED) {

    override val label = "Components"

    override suspend fun component() {

        interchange(ComponentInterchange())

    }

}