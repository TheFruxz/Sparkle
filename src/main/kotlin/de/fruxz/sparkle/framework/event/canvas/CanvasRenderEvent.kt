package de.fruxz.sparkle.framework.event.canvas

import de.fruxz.sparkle.framework.visual.canvas.Canvas
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.Inventory

data class CanvasRenderEvent(
	val player: Player,
	override val canvas: Canvas,
	val renderResult: Inventory,
) : CanvasEvent(player, canvas) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}
