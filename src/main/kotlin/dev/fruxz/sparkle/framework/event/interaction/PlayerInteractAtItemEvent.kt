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
    val item: ItemStack,
    val material: Material,
    @get:JvmName("kIsCancelled") @set:JvmName("kIsCancelled") var isCancelled: Boolean = false,
    override val action: Action,
    override val origin: PlayerInteractEvent,
    override var blockInteraction: Result = Result.DEFAULT,
    override var itemInteraction: Result = Result.DEFAULT,
) : SparkleInteractEvent<PlayerInteractEvent>, PlayerEvent(whoInteract, false), Cancellable {

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers() = handlerList

    fun denyItemInteraction() {
        this.itemInteraction = Result.DENY
    }

    companion object {

        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}