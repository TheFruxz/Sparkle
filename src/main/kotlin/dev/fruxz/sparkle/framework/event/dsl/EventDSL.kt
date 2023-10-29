package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.sparkle.framework.system.sparkle
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.Plugin
import kotlin.reflect.KClass

@SparkleEventDSL
fun <T : Event> listen(
    eventClazz: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    action: (event: T) -> Unit,
) = JITEventManager.addGenericEvent(
    clazz = eventClazz,
    action = object : JITEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        plugin = plugin,
    ) {
        override fun invoke(event: T) = action(event)
    }
)

@SparkleEventDSL
inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    noinline action: (event: T) -> Unit,
) = listen(
    eventClazz = T::class,
    priority = priority,
    ignoreCancelled = ignoreCancelled,
    plugin = plugin,
    action = action,
)

@SparkleEventDSL
fun <T : EntityEvent> Entity.listenOnEntity(
    eventClazz: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    action: (event: T, entity: Entity) -> Unit,
) = JITEventManager.addEntityEvent(
    clazz = eventClazz,
    uuid = uniqueId,
    action = object : JITEntityEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, entity: Entity) = action(event, entity)
    }
)

@SparkleEventDSL
inline fun <reified T : EntityEvent> Entity.listenOnEntity(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, entity: Entity) -> Unit,
) = listenOnEntity(
    eventClazz = T::class,
    priority = priority,
    ignoreCancelled = ignoreCancelled,
    autoRemoval = autoRemoval,
    plugin = plugin,
    action = action,
)

@SparkleEventDSL
fun <T : PlayerEvent> Player.listenOnPlayer(
    eventClazz: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    action: (event: T, player: Player) -> Unit,
) = JITEventManager.addPlayerEvent(
    clazz = eventClazz,
    uuid = uniqueId,
    action = object : JITPlayerEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, player: Player) = action(event, player)
    }
)

@SparkleEventDSL
inline fun <reified T : PlayerEvent> Player.listenOnPlayer(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, player: Player) -> Unit,
) = listenOnPlayer(
    eventClazz = T::class,
    priority = priority,
    ignoreCancelled = ignoreCancelled,
    autoRemoval = autoRemoval,
    plugin = plugin,
    action = action,
)