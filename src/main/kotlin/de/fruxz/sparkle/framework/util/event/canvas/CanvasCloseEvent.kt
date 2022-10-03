package de.fruxz.sparkle.framework.util.event.canvas

import de.fruxz.sparkle.framework.util.visual.canvas.Canvas
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryView

data class CanvasCloseEvent(
	val player: Player,
	override val canvas: Canvas,
	val view: InventoryView,
	val originEvent: InventoryCloseEvent,
) : CanvasEvent(player, canvas) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}
