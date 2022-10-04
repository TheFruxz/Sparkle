package de.fruxz.sparkle.framework.event.canvas

import de.fruxz.sparkle.framework.extension.visual.ui.affectedItem
import de.fruxz.sparkle.framework.visual.canvas.Canvas
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

data class CanvasClickEvent(
	val player: Player,
	override val canvas: Canvas,
	val slot: Int,
	val view: InventoryView,
	val originEvent: InventoryClickEvent,
	val click: ClickType = originEvent.click
) : CanvasEvent(player, canvas, isAsync = false) {

	val action by lazy {
		originEvent.action
	}

	val slotType by lazy {
		originEvent.slotType
	}

	val rawSlot by lazy {
		originEvent.rawSlot
	}

	val clickedItem by lazy {
		originEvent.currentItem
	}

	val affectedItem by lazy {
		originEvent.affectedItem
	}

	/**
	 * The clicked inventory, or null if clicked outside the inventory.
	 */
	val clickedInventory: Inventory? by lazy {
		originEvent.clickedInventory
	}

	/**
	 * This value is only a wrapper for the original [slot] value,
	 * this is here to create consistency across the API.
	 * @author Fruxz
	 * @since 1.0
	 */
	val clickedSlot: Int by lazy {
		slot
	}

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}
