package de.jet.library.extension.paper

import org.bukkit.event.block.Action
import org.bukkit.event.block.Action.*

val Action.isRightClick: Boolean
	get() = arrayOf(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK).contains(this)

val Action.isLeftClick: Boolean
	get() = arrayOf(LEFT_CLICK_AIR, LEFT_CLICK_BLOCK).contains(this)

val Action.isPhysical: Boolean
	get() = this == PHYSICAL