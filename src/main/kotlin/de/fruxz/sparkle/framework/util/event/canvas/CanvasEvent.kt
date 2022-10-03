package de.fruxz.sparkle.framework.util.event.canvas

import de.fruxz.sparkle.framework.util.visual.canvas.Canvas
import net.kyori.adventure.key.Key
import org.bukkit.entity.HumanEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

abstract class CanvasEvent(
	val host: HumanEntity,
	open val canvas: Canvas,
	val isAsync: Boolean = true,
	@set:JvmName("kotlinCancelled") var cancelled: Boolean = false,
) : Event(isAsync), Cancellable {

	override fun isCancelled() = cancelled

	override fun setCancelled(cancel: Boolean) {
		cancelled = cancel
	}

}