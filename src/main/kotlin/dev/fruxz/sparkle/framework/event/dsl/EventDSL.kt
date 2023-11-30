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
): ActiveJITEvent<JITEvent<T>, T> {
    val jitEvent = object : JITEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        plugin = plugin,
    ) {
        override fun invoke(event: T) = action(event)
    }


    JITEventManager.addGenericEvent(
        clazz = eventClazz,
        action = jitEvent,
    )

    return ActiveJITEvent(jitEvent = jitEvent)
}

@SparkleEventDSL
inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    noinline action: (event: T) -> Unit,
): ActiveJITEvent<JITEvent<T>, T> = listen(
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
): ActiveJITEvent<JITEntityEvent<T>, T> {
    val jitEvent = object : JITEntityEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, entity: Entity) = action(event, entity)
    }

    JITEventManager.addEntityEvent(
        clazz = eventClazz,
        uuid = uniqueId,
        action = jitEvent,
    )

    return ActiveJITEvent(jitEvent = jitEvent)
}

@SparkleEventDSL
inline fun <reified T : EntityEvent> Entity.listenOnEntity(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, entity: Entity) -> Unit,
): ActiveJITEvent<JITEntityEvent<T>, T> = listenOnEntity(
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
): ActiveJITEvent<JITPlayerEvent<T>, T> {
    val jitEvent = object : JITPlayerEvent<T>(
        eventClazz = eventClazz,
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, player: Player) = action(event, player)
    }

    JITEventManager.addPlayerEvent(
        clazz = eventClazz,
        uuid = uniqueId,
        action = jitEvent,
    )

    return ActiveJITEvent(jitEvent = jitEvent)
}

@SparkleEventDSL
inline fun <reified T : PlayerEvent> Player.listenOnPlayer(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, player: Player) -> Unit,
): ActiveJITEvent<JITPlayerEvent<T>, T> = listenOnPlayer(
    eventClazz = T::class,
    priority = priority,
    ignoreCancelled = ignoreCancelled,
    autoRemoval = autoRemoval,
    plugin = plugin,
    action = action,
)