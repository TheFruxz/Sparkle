package dev.fruxz.sparkle.framework.event.interaction

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental // TODO implementation in module
data class PlayerInteractAtBlockEvent(
    val whoInteract: Player,
    val block: Block,
    val material: Material,
    val action: Action,
    override val origin: PlayerInteractEvent,
    override var blockInteraction: Result = Result.DEFAULT,
    override var itemInteraction: Result = Result.DEFAULT,
) : SparkleInteractEvent, PlayerEvent(whoInteract, false) {

    override fun getHandlers() = handlerList

    companion object {

        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}