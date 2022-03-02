package de.jet.paper.runtime.event

import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.panel.Panel
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryType.SlotType

class PanelClickEvent(
	val player: Player,
	val panel: Panel,
	val action: InventoryAction,
	val clickedSlot: Int,
	val clickedSlotType: SlotType,
	val clickedItem: Item?,
) : Event() {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}