package de.jet.library.tool.display.item.action

import de.jet.library.runtime.event.interact.PlayerInteractAtItemEvent
import org.bukkit.event.Event
import org.bukkit.event.inventory.InventoryClickEvent

sealed interface ItemAction<T : Event> {

	var action: T.() -> Unit

	var async: Boolean

	var stop: Boolean

}

data class ItemClickAction(
	override var action: InventoryClickEvent.() -> Unit,
	override var async: Boolean = true,
	override var stop: Boolean = true,
) : ItemAction<InventoryClickEvent>

data class ItemInteractAction(
	override var action: PlayerInteractAtItemEvent.() -> Unit,
	override var async: Boolean = true,
	override var stop: Boolean = true,
) : ItemAction<PlayerInteractAtItemEvent>
