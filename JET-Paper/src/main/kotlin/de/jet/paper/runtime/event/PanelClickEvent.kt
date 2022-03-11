package de.jet.paper.runtime.event

import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.panel.Panel
import de.jet.paper.tool.event.KCancellable
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class PanelClickEvent(
	player: Player,
	val panel: Panel,
	val action: InventoryAction,
	val clickedSlot: Int,
	val inventoryView: InventoryView,
	val clickedSlotType: SlotType,
	val clickedItem: Item?,
	val clickedItemStack: ItemStack?,
	val clickType: ClickType,
	val origin: InventoryClickEvent,
	override var eventCancelled: Boolean,
) : PlayerEvent(player), KCancellable {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}


}