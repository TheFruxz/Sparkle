package dev.fruxz.sparkle.framework.event.attack

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental // TODO implementation in module
data class PlayerDamageByPlayerEvent(
    val victim: Player,
    val attacker: Player,
    private var isCancelled: Boolean = false,
) : PlayerEvent(victim, false), Cancellable {

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
