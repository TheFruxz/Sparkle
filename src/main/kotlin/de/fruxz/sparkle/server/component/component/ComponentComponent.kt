package de.fruxz.sparkle.server.component.component

import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent

internal class ComponentComponent : SmartComponent(RunType.ENABLED) {

    override val label = "Components"

    override suspend fun component() {

        interchange(ComponentInterchange())

    }

}