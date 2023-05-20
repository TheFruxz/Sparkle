package dev.fruxz.sparkle.server.component

import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.StartupBehavior
import dev.fruxz.sparkle.server.command.DemoCmmand
import net.kyori.adventure.key.Key
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class TestComponent : Component(StartupBehavior.DEFAULT_AUTOSTART) {

    override val identity: Key = Key.key("test")

    val test: KType
        get() = this::class.createType()

    override fun setup() {

        add(DemoCmmand())

    }

}