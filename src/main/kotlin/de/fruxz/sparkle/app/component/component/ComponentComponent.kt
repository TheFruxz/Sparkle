package de.fruxz.sparkle.app.component.component

import de.fruxz.sparkle.structure.component.SmartComponent

internal class ComponentComponent : SmartComponent(RunType.ENABLED) {

    override val label = "Components"

    override suspend fun component() {

        interchange(ComponentInterchange())

    }

}