package dev.fruxz.sparkle.server.component

import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.StartupBehavior
import net.kyori.adventure.key.Key

class TestComponent : Component(StartupBehavior.DEFAULT_AUTOSTART) {

    override val identity: Key = Key.key("test")

}