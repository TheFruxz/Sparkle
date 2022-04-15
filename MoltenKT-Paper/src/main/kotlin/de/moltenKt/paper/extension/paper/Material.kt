package de.moltenKt.paper.extension.paper

import com.destroystokyo.paper.MaterialTags
import org.bukkit.block.Block

val Block.isGlass: Boolean
	get() = MaterialTags.GLASS.isTagged(this)