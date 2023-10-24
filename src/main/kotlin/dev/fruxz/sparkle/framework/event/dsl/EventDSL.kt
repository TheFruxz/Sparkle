package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.addCachedClassifiedEvent
import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.cachedEntityEvents
import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.cachedEvents
import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.cachedPlayerEvents
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.system.pluginManager
import dev.fruxz.sparkle.framework.system.sparkle
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import java.util.*
import kotlin.reflect.KClass

object CachedEventsManager {

    val cachedEvents = mutableMapOf<KClass<out Event>, List<CachedGenericEvent<*>>>()
    val cachedPlayerEvents = mutableMapOf<KClass<out Event>, Map<UUID, List<CachedPlayerEvent<*>>>>()
    val cachedEntityEvents = mutableMapOf<KClass<out Event>, Map<UUID, List<CachedEntityEvent<*>>>>()

    fun <T : Event> append(
        cachedEvent: CachedEvent<T, *>,
    ) {
        val clazz = cachedEvent.eventClass

        when (cachedEvent) {
            is CachedGenericEvent<T> -> { cachedEvents[clazz] = (cachedEvents[clazz].orEmpty() + cachedEvent).distinct() }
            is CachedPlayerEvent<T> -> {
                cachedPlayerEvents[clazz] = cachedPlayerEvents[clazz].orEmpty().toMutableMap().also { map ->
                    map[cachedEvent.affectedPlayer] = map[cachedEvent.affectedPlayer].orEmpty() + cachedEvent
                }
            }
            is CachedEntityEvent<T> -> {
                cachedEntityEvents[clazz] = cachedEntityEvents[clazz].orEmpty().toMutableMap().also { map ->
                    map[cachedEvent.affectedEntity] = map[cachedEvent.affectedEntity].orEmpty() + cachedEvent
                }
            }
        }
    }

    fun <T : Event> addCachedEvent(
        data: MutableMap<KClass<out T>, List<(T) -> Unit>>,
        clazz: KClass<out T>,
        action: (T) -> Unit
    ) {
        data[clazz] = (data[clazz].orEmpty() + action).distinct()
    }

    fun <T : Event, E> addCachedClassifiedEvent(
        data: MutableMap<KClass<out Event>, MutableMap<UUID, List<(T, E) -> Unit>>>,
        clazz: KClass<out T>,
        uuid: UUID,
        action: (T, E) -> Unit
    ) {
        data[clazz] = data[clazz].orEmpty().toMutableMap().also { map ->
            map[uuid] = map[uuid].orEmpty() + action
        }
    }

}



inline fun <reified T : Event> registerEventListener(
    clazz: KClass<T>,
    noinline action: (event: T) -> Unit,
    plugin: Plugin,
    listener: Listener,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    executor: EventExecutor = EventExecutor { _, event ->
        when (event) {
            is T -> action(event)
            else -> error("Event of type ${event::class.qualifiedName} is not of type ${T::class.qualifiedName}!")
        }
    },
): CachedEvent<T, *> {

    pluginManager.registerEvent(
        /* event = */ clazz.java,
        /* listener = */ listener,
        /* priority = */ priority,
        /* executor = */ executor,
        /* plugin = */ plugin,
        /* ignoreCancelled = */ ignoreCancelled,
    )

    return CachedGenericEvent(
        eventClass = clazz,
        action = action,
        executor = executor,
    )
}

@SparkleDSL
@Deprecated(message = "Use event extension function instead!", replaceWith = ReplaceWith("event<T>(action = action)"))
inline fun <reified T : Event> listen(
    noinline action: (event: T) -> Unit,
) = event<T>(action = action)

inline fun <reified T : Event> event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    noinline action: (event: T) -> Unit,
) {

    if (!cachedEvents.containsKey(T::class)) {

        registerEventListener(
            clazz = T::class,
            action = action,
            plugin = plugin,
            listener = object : Listener {},
            priority = priority,
            ignoreCancelled = ignoreCancelled
        )

        debugLog { "Registered event listener for ${T::class.simpleName}!" }

    }

    CachedEventsManager.append(CachedGenericEvent(T::class, action) { _, event ->
        when (event) {
            is T -> action(event)
            else -> error("Event of type ${event::class.qualifiedName} is not of type ${T::class.qualifiedName}!")
        }
    })

}

@SparkleDSL
inline fun <reified T : EntityEvent> Entity.entityEvent(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    noinline action: (event: T, entity: Entity) -> Unit,
) {

    if (!cachedEntityEvents.containsKey(T::class)) {

        registerEventListener(
            clazz = T::class,
            action = action,
            plugin = plugin,
            listener = object : Listener {},
            priority = priority,
            ignoreCancelled = ignoreCancelled
        )

        debugLog { "Registered event listener for ${T::class.simpleName}!" }

    }

    CachedEventsManager.append(CachedGenericEvent(T::class, action) { _, event ->
        when (event) {
            is T -> action(event)
            else -> error("Event of type ${event::class.qualifiedName} is not of type ${T::class.qualifiedName}!")
        }
    })

}

@SparkleDSL
inline fun <reified T : EntityEvent> Entity.listenOnEntity(
    crossinline action: (event: T, entity: Entity) -> Unit,
) {
    if (!cachedEntityEvents.containsKey(T::class)) {
        event<T> { event ->
            cachedEntityEvents[T::class].orEmpty().forEach { (key, value) ->
                if (event.entity.uniqueId == key) {
                    value.forEach { it(event, event.entity) }
                }
            }
        }
    }

    addCachedClassifiedEvent(cachedEntityEvents, T::class, uniqueId) { event, entity ->
        when (event) {
            is T -> action(event, entity)
            else -> error("Event type mismatch!")
        }
    }

}

@SparkleDSL
@JvmName("listenOnPlayerEntity")
inline fun <reified T : PlayerEvent> Player.listenOnPlayer(
    @Suppress("UNUSED_PARAMETER") removeOnQuit: Boolean = true, // TODO module, which will clean this up
    crossinline action: (event: T, player: Player) -> Unit,
) {

    if (!cachedPlayerEvents.containsKey(T::class)) {
        event<T> { event ->
            cachedPlayerEvents[T::class].orEmpty().forEach { (key, value) ->
                if (event.player.uniqueId == key) {
                    value.forEach { it(event, event.player) }
                }
            }
        }
    }

    addCachedClassifiedEvent(cachedPlayerEvents, T::class, uniqueId) { event, player ->
        when (event) {
            is T -> action(event, player)
            else -> error("Event type mismatch!")
        }
    }

}