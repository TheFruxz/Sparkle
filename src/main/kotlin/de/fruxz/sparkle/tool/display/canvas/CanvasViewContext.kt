package de.fruxz.sparkle.tool.display.canvas

import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

data class CanvasViewContext(
	val canvas: Canvas,
	val receiver: Player,
	val data: Map<Key, Any>,
)
