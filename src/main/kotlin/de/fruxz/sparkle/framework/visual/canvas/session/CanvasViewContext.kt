package de.fruxz.sparkle.framework.visual.canvas.session

import de.fruxz.sparkle.framework.visual.canvas.Canvas
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

data class CanvasViewContext(
	val canvas: Canvas,
	val receiver: Player,
	val data: Map<Key, Any>,
)
