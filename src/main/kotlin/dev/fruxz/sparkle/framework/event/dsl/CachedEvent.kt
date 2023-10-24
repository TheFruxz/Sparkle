package dev.fruxz.sparkle.framework.event.dsl

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.plugin.EventExecutor
import java.util.*
import kotlin.reflect.KClass

sealed interface CachedEvent<T : Event, A> {
    val eventClass: KClass<T>
    val action: A
    val executor: EventExecutor
}

data class CachedGenericEvent<T : Event>(
    override val eventClass: KClass<T>,
    override val action: (event: T) -> Unit,
    override val executor: EventExecutor,
) : CachedEvent<T, (event: T) -> Unit>

data class CachedPlayerEvent<T : Event>(
    override val eventClass: KClass<T>,
    override val action: (event: T, player: Player) -> Unit,
    override val executor: EventExecutor,
    val affectedPlayer: UUID,
) : CachedEvent<T, (event: T, player: Player) -> Unit>

data class CachedEntityEvent<T : Event>(
    override val eventClass: KClass<T>,
    override val action: (event: T, entity: Entity) -> Unit,
    override val executor: EventExecutor,
    val affectedEntity: UUID,
) : CachedEvent<T, (event: T, entity: Entity) -> Unit>