package de.jet.paper.app.component.linking

import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.ui.get
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.paper.createKey
import de.jet.paper.extension.system
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.panel.PanelFlag
import de.jet.paper.tool.display.ui.panel.PanelFlag.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGH
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryOpenEvent

internal class ItemLinkListener : EventListener() {

	private fun getFlags(item: Item?): Set<PanelFlag> {
		val panelIdentity = item?.identityObject
		return JetCache.registeredPanelFlags[panelIdentity?.identity] ?: emptySet()
	}

	@EventHandler
	fun inventoryClick(event: InventoryClickEvent) {
		val inventory = event.clickedInventory

		if (inventory == event.view.topInventory) {

			if (getFlags(inventory[4]?.item).contains(NOT_CLICK_ABLE)) {
				event.isCancelled = true
			}

			if (event.currentItem?.let { it.item.dataGet(system.createKey("panelBorder")) == 1 } == true) {

				// everytime when the panelBorder property is set, it wants to be protected!
				event.isCancelled = true

			}

		}

	}

	@EventHandler(priority = HIGH)
	fun inventoryOpen(event: InventoryOpenEvent) {
		if (getFlags(event.inventory[4]?.item).contains(NOT_OPEN_ABLE))
			event.isCancelled = true
	}

	@EventHandler(priority = HIGH)
	fun inventoryClose(event: InventoryCloseEvent) {
		if (getFlags(event.inventory[4]?.item).contains(NOT_CLOSE_ABLE))
			event.player.openInventory(event.inventory)
	}

	@EventHandler(priority = HIGH)
	fun inventoryDrag(event: InventoryDragEvent) {
		if (getFlags(event.inventory[4]?.item).contains(NOT_DRAG_ABLE))
			event.isCancelled = true
	}

	@EventHandler(priority = HIGH)
	fun inventoryMove(event: InventoryMoveItemEvent) {
		if ((getFlags(event.destination[4]?.item) + getFlags(event.initiator[4]?.item)).contains(NOT_MOVE_ABLE))
			event.isCancelled = true
	}

}