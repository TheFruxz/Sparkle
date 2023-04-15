package dev.fruxz.sparkle.framework.event

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.player.PlayerInteractEvent

val Action.isPhysical: Boolean
    get() = this == Action.PHYSICAL

val PlayerInteractEvent.possibleFutureBlock: Location?
    get() = clickedBlock?.let {
        when {
            it.isReplaceable -> it.location
            else -> it.location.toBlockLocation().add(blockFace.direction)
        }
    }

val <T : InventoryInteractEvent> T.player: Player
    get() = whoClicked as Player