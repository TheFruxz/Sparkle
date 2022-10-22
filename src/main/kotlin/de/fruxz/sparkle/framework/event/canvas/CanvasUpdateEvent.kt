package de.fruxz.sparkle.framework.event.canvas

import de.fruxz.sparkle.framework.visual.canvas.Canvas
import de.fruxz.sparkle.framework.visual.canvas.Canvas.ExperimentalCanvasApi
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.Inventory

class CanvasUpdateEvent(
	player: Player,
	canvas: Canvas,
	inventory: Inventory,
	data: Map<Key, Any>,
	val updateReason: UpdateReason, // TODO implement, when pagination utilizes the update function!
) : CanvasOpenEvent(player, canvas, inventory, data) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

	enum class UpdateReason {
		PLUGIN,
		@ExperimentalCanvasApi SCROLL,
		@ExperimentalCanvasApi PAGE_TURN;
	}

}
