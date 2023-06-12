package dev.fruxz.sparkle.server.component.demo

import dev.fruxz.sparkle.framework.modularity.component.Component
import net.kyori.adventure.key.Key

class DemoComponent : Component() {
    override val identity = Key.key("sparkle", "demo")

    override fun setup() {

        add(DemoListener())

    }
}