package dev.fruxz.sparkle.framework.event.attack

import dev.fruxz.sparkle.framework.event.SpecializedEvent
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerEvent
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental // TODO implementation in module
data class PlayerDamageByPlayerEvent(
    val victim: Player,
    val attacker: Player,
    override val origin: EntityDamageByEntityEvent,
    private var isCancelled: Boolean = false,
) : SpecializedEvent<EntityDamageByEntityEvent>, PlayerEvent(victim, false), Cancellable {

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers() = handlerList

    companion object {

        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}
