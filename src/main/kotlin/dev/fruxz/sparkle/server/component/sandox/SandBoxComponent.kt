package dev.fruxz.sparkle.server.component.sandox

import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.StartupBehavior
import net.kyori.adventure.key.Key

class SandBoxComponent : Component(StartupBehavior.DEFAULT_AUTOSTART) {

    override val identity = Key.key("sparkle", "sandbox")

    override fun setup() {

        add(SandBoxCommand())

    }

}