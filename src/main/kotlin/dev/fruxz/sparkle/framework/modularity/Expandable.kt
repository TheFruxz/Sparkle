package dev.fruxz.sparkle.framework.modularity

import dev.fruxz.sparkle.framework.modularity.component.Component
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import kotlin.reflect.KClass

interface Expandable {

    fun add(component: Component)

    fun add(listener: Listener)

    fun add(command: CommandExecutor, clazz: KClass<out CommandExecutor>)

}