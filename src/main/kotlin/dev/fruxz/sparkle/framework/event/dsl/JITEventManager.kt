package dev.fruxz.sparkle.framework.event.dsl

import dev.fruxz.sparkle.framework.system.pluginManager
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.system.worlds
import org.bukkit.entity.Entity
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
                        if (jitEvent.eventClazz != clazz) return
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
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
        addEvent(clazz, action)
    }

    fun <T : PlayerEvent> addPlayerEvent(
        clazz: KClass<T>,
        uuid: UUID,
        action: JITPlayerEvent<T>
    ) {
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
        playerEvents[uuid] = (playerEvents[uuid].orEmpty() + action).distinct()
    }

    fun <T : EntityEvent> addEntityEvent(
        clazz: KClass<T>,
        uuid: UUID,
        action: JITEntityEvent<T>
    ) {
        initializeListenerIfNotPresent(
            clazz = clazz,
            priority = action.priority,
            ignoreCancelled = action.ignoreCancelled,
            plugin = action.plugin,
        )
        entityEvents[uuid] = (entityEvents[uuid].orEmpty() + action).distinct()
    }

    fun performCleanup() {
        val entities = worlds.flatMap { it.entities.map(Entity::getUniqueId) }

        playerEvents.removeAll { _, value -> value.isEmpty() }
        entityEvents.removeAll { _, value -> value.isEmpty() }

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