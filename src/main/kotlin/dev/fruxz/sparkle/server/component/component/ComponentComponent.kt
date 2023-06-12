package dev.fruxz.sparkle.server.component.component

import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.StartupBehavior
import net.kyori.adventure.key.Key

class ComponentComponent : Component(StartupBehavior.ALWAYS_ON) {

    override val identity = Key.key("sparkle", "component")

    override fun setup() {

        add(ComponentCommand())

    }

}