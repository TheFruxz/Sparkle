package de.moltenKt.paper.runtime.event

import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.tool.display.item.Item
import de.moltenKt.paper.tool.display.ui.panel.Panel
import de.moltenKt.paper.tool.event.KCancellable
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class PanelClickEvent(
	player: Player,
	val origin: InventoryClickEvent,
	val panel: Panel,
	val action: InventoryAction = origin.action,
	val clickedSlot: Int = origin.slot,
	val inventoryView: InventoryView = origin.view,
	val clickedSlotType: SlotType = origin.slotType,
	val clickedItem: Item? = origin.currentItem?.item,
	val clickedItemStack: ItemStack? = origin.currentItem,
	val clickType: ClickType = origin.click,
	val clickedInventory: Inventory? = origin.clickedInventory,
	override var eventCancelled: Boolean = false,
) : PlayerEvent(player), KCancellable {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}


}