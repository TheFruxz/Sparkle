package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.pluginManager
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.system.worlds
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.Plugin
import java.util.*
import kotlin.reflect.KClass

object JITEventManager {
    private val events = mutableMapOf<KClass<out Event>, List<JITEvent<out Event>>>()
    private val playerEvents = mutableMapOf<UUID, List<JITPlayerEvent<out PlayerEvent>>>()
    private val entityEvents = mutableMapOf<UUID, List<JITEntityEvent<out EntityEvent>>>()

    private fun <T : Event> addEvent(clazz: KClass<T>, action: JITEvent<T>) {
        events[clazz] = (events[clazz].orEmpty() + action).distinct()
    }

    private fun <T : Event> initializeListenerIfNotPresent(
        clazz: KClass<T>,
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = false,
        plugin: Plugin = sparkle,
    ) {
        if (events[clazz].orEmpty().none { it.priority == priority && it.ignoreCancelled == ignoreCancelled && it.plugin == plugin }) {
            pluginManager.registerEvent(
                /* event = */ clazz.java,
                /* listener = */ object : Listener {},
                /* priority = */ priority,
                /* executor = */ { _, event ->

                    fun checkedInvoke(jitEvent: JITEvent<*>) {
                        if (jitEvent.ignoreCancelled != ignoreCancelled) return
                        if (jitEvent.priority != priority) return
                        if (jitEvent.plugin != plugin) return

                        jitEvent.blindInvoke(event)
                    }

                    events[clazz].orEmpty().forEach(::checkedInvoke)

                    if (event is PlayerEvent) playerEvents[event.player.uniqueId].orEmpty().forEach(::checkedInvoke)
                    if (event is EntityEvent) entityEvents[event.entity.uniqueId].orEmpty().forEach(::checkedInvoke)

                },
                /* plugin = */ plugin,
                /* ignoreCancelled = */ ignoreCancelled,
            )
        }
    }

    fun <T : Event> addGenericEvent(
        clazz: KClass<T>,
        action: JITEvent<T>
    ) {
        addEvent(clazz, action)
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
    }

    fun <T : PlayerEvent> addPlayerEvent(
        clazz: KClass<T>,
        uuid: UUID,
        action: JITPlayerEvent<T>
    ) {
        playerEvents[uuid] = (playerEvents[uuid].orEmpty() + action).distinct()
        addEvent(clazz, action)
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
    }

    fun <T : EntityEvent> addEntityEvent(
        clazz: KClass<T>,
        uuid: UUID,
        action: JITEntityEvent<T>
    ) {
        entityEvents[uuid] = (entityEvents[uuid].orEmpty() + action).distinct()
        addEvent(clazz, action)
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
    }

    fun performCleanup() {
        val entities = worlds.flatMap { it.entities.map(Entity::getUniqueId) }

        playerEvents.keys.filterNot(entities::contains).forEach {
            playerEvents[it] = playerEvents[it].orEmpty().filterNot(JITPlayerEvent<*>::autoRemoval)

            if (playerEvents[it].isNullOrEmpty()) playerEvents.remove(it)
        }

        entityEvents.keys.filterNot(entities::contains).forEach {
            entityEvents[it] = entityEvents[it].orEmpty().filterNot(JITEntityEvent<*>::autoRemoval)

            if (entityEvents[it].isNullOrEmpty()) entityEvents.remove(it)
        }

    }

    fun performRemoval(uuid: UUID, requiresAutoRemoval: Boolean = true) {

        playerEvents[uuid] = playerEvents[uuid].orEmpty().filterNot { it.autoRemoval == requiresAutoRemoval }
        entityEvents[uuid] = entityEvents[uuid].orEmpty().filterNot { it.autoRemoval == requiresAutoRemoval }

        if (playerEvents[uuid].isNullOrEmpty()) playerEvents.remove(uuid)
        if (entityEvents[uuid].isNullOrEmpty()) entityEvents.remove(uuid)
    }

}

inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    plugin: Plugin = sparkle,
    noinline action: (event: T) -> Unit,
) = JITEventManager.addGenericEvent(
    clazz = T::class,
    action = object : JITEvent<T>(
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        plugin = plugin,
    ) {
        override fun invoke(event: T) = action(event)
    }
)

@SparkleDSL
inline fun <reified T : EntityEvent> Entity.listenOnEntity(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, entity: Entity) -> Unit,
) = JITEventManager.addEntityEvent(
    clazz = T::class,
    uuid = uniqueId,
    action = object : JITEntityEvent<T>(
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, entity: Entity) = action(event, entity)
    }
)

@SparkleDSL
inline fun <reified T : PlayerEvent> Player.listenOnPlayer(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    autoRemoval: Boolean = true,
    plugin: Plugin = sparkle,
    noinline action: (event: T, player: Player) -> Unit,
) = JITEventManager.addPlayerEvent(
    clazz = T::class,
    uuid = uniqueId,
    action = object : JITPlayerEvent<T>(
        priority = priority,
        ignoreCancelled = ignoreCancelled,
        autoRemoval = autoRemoval,
        plugin = plugin,
    ) {
        override fun invoke(event: T, player: Player) = action(event, player)
    }
)