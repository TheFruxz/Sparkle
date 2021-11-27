package de.jet.minecraft.extension.paper

import de.jet.minecraft.runtime.event.interact.PlayerInteractAtBlockEvent
import org.bukkit.Location
import org.bukkit.event.block.Action
import org.bukkit.event.block.Action.*
import org.bukkit.event.player.PlayerInteractEvent

val Action.isPhysical: Boolean
	get() = this == PHYSICAL

val PlayerInteractAtBlockEvent.realAffectedBlock: Location
	get() = block.location.toBlockLocation().add(origin.blockFace.direction)

val PlayerInteractEvent.realAffectedBlock: Location?
	get() = clickedBlock?.location?.toBlockLocation()?.add(blockFace.direction)
