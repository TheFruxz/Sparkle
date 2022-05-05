package de.moltenKt.paper.runtime.event.canvas

import de.moltenKt.paper.tool.display.ui.canvas.Canvas
import net.kyori.adventure.key.Key
import org.bukkit.entity.HumanEntity
import org.bukkit.event.Event

abstract class CanvasEvent(
	val host: HumanEntity,
	open val canvas: Canvas,
	val key: Key = canvas.key,
) : Event(false /* TODO maybe change if data in items can be safely changed async */)