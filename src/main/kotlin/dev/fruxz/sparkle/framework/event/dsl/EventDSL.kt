package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.addCachedClassifiedEvent
import dev.fruxz.sparkle.framework.event.dsl.CachedEventsManager.addCachedEvent
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

    val cachedEvents = mutableMapOf<KClass<out Event>, List<(Event) -> Unit>>()
    val cachedPlayerEvents = mutableMapOf<KClass<out Event>, MutableMap<UUID, List<(Event, Player) -> Unit>>>()
    val cachedEntityEvents = mutableMapOf<KClass<out Event>, MutableMap<UUID, List<(Event, Entity) -> Unit>>>()

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
    clazz: KClass<out T>,
    crossinline action: (event: T) -> Unit,
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
) {
    pluginManager.registerEvent(
        /* event = */ clazz.java,
        /* listener = */ listener,
        /* priority = */ priority,
        /* executor = */ executor,
        /* plugin = */ plugin,
        /* ignoreCancelled = */ ignoreCancelled,
    )
}

@SparkleDSL
@Deprecated(message = "Use event extension function instead!", replaceWith = ReplaceWith("event<T>(action = action)"))
inline fun <reified T : Event> listen(
    crossinline action: (event: T) -> Unit,
) = event<T>(action = action)

inline fun <reified T : Event> event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    crossinline action: (event: T) -> Unit,
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

    addCachedEvent(cachedEvents, T::class) { event ->
        when (event) {
            is T -> action(event)
            else -> error("Event type mismatch!")
        }
    }

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