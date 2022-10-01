package de.moltenKt.paper.extension.paper

import de.moltenKt.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.block.Action.PHYSICAL
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * This value represents the equal-check if the
 * action is [PHYSICAL].
 * @sample isPhysical
 */
val Action.isPhysical: Boolean
	get() = this == PHYSICAL

/**
 * If a block is clicked, the location, where a new block will be placed returns.
 * This means, if you place a block on the top of another block, the location
 * gets returned of the possible/futuristic block-location of the newly appearing/
 * placed block.
 */
val PlayerInteractAtBlockEvent.realAffectedBlock: Location
	get() = block.location.toBlockLocation().add(origin.blockFace.direction)

/**
 * If a block is clicked, the location, where a new block will be placed returns.
 * This means, if you place a block on the top of another block, the location
 * gets returned of the possible/futuristic block-location of the newly appearing/
 * placed block.
 */
val PlayerInteractEvent.realAffectedBlock: Location?
	get() = clickedBlock?.location?.toBlockLocation()?.add(blockFace.direction)

/**
 * Returns the [InventoryInteractEvent.getWhoClicked] cast to [Player].
 * @author Fruxz
 * @since 1.0
 */
val <T : InventoryInteractEvent> T.player
	get() = whoClicked as Player
