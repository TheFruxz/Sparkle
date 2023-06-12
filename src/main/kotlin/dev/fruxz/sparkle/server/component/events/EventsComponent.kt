package dev.fruxz.sparkle.server.component.events

import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.StartupBehavior
import net.kyori.adventure.key.Key

class EventsComponent : Component(StartupBehavior.DEFAULT_AUTOSTART) {

    override val identity = Key.key("sparkle", "events")

    override fun setup() {

        add(InteractionListener())
        add(DamageListener())

    }

}