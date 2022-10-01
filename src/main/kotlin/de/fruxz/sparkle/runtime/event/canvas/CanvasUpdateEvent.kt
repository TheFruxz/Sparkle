package de.fruxz.sparkle.runtime.event.canvas

import de.fruxz.sparkle.tool.display.canvas.Canvas
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.Inventory

class CanvasUpdateEvent(
	player: Player,
	canvas: Canvas,
	inventory: Inventory,
	data: Map<Key, Any>,
) : CanvasOpenEvent(player, canvas, inventory, data) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}
