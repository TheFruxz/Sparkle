package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.sparkle.framework.system.sparkle
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.Plugin
import kotlin.reflect.KClass

abstract class JITEvent<T : Event>(
    val eventClazz: KClass<T>,
    val priority: EventPriority = EventPriority.NORMAL,
    val ignoreCancelled: Boolean = false,
    val plugin: Plugin = sparkle,
) {
    abstract operator fun invoke(event: T)
    fun blindInvoke(event: Event) = invoke(event.forceCast())
}

abstract class JITPlayerEvent<T : PlayerEvent>(
    eventClazz: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    val autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
) : JITEvent<T>(eventClazz, priority, ignoreCancelled, plugin) {
    abstract operator fun invoke(event: T, player: Player)
    override fun invoke(event: T) = invoke(event, event.player)
}

abstract class JITEntityEvent<T : EntityEvent>(
    eventClazz: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    val autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
) : JITEvent<T>(eventClazz, priority, ignoreCancelled, plugin) {
    abstract operator fun invoke(event: T, entity: Entity)
    override fun invoke(event: T) = invoke(event, event.entity)
}