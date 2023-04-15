package dev.fruxz.sparkle.framework.event.interaction

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental // TODO implementation in module
data class PlayerInteractAtItemEvent(
    val whoInteract: Player,
    val item: ItemStack, // TODO sparkle items
    val material: Material,
    val action: Action,
    private var isCancelled: Boolean = false,
    override val origin: PlayerInteractEvent,
    override var blockInteraction: Result = Result.DEFAULT,
    override var itemInteraction: Result = Result.DEFAULT,
) : SparkleInteractEvent, PlayerEvent(whoInteract, false), Cancellable {

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