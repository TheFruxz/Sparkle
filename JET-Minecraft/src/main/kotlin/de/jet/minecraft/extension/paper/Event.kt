package de.jet.library.extension.paper

import de.jet.minecraft.runtime.event.interact.PlayerInteractAtBlockEvent
import org.bukkit.Location
import org.bukkit.event.block.Action
import org.bukkit.event.block.Action.*
import org.bukkit.event.player.PlayerInteractEvent

val Action.isRightClick: Boolean
	get() = arrayOf(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK).contains(this)

val Action.isLeftClick: Boolean
	get() = arrayOf(LEFT_CLICK_AIR, LEFT_CLICK_BLOCK).contains(this)

val Action.isPhysical: Boolean
	get() = this == PHYSICAL

val PlayerInteractAtBlockEvent.realAffectedBlock: Location
	get() = block.location.toBlockLocation().add(origin.blockFace.direction)

val PlayerInteractEvent.realAffectedBlock: Location?
	get() = clickedBlock?.location?.toBlockLocation()?.add(blockFace.direction)
