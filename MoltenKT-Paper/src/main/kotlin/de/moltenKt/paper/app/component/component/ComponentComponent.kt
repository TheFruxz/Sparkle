package de.moltenKt.paper.app.component.component

import de.moltenKt.paper.structure.component.SmartComponent

internal class ComponentComponent : SmartComponent(RunType.ENABLED) {

    override val thisIdentity = "Components"

    override suspend fun component() {

        interchange(ComponentInterchange())

    }

}