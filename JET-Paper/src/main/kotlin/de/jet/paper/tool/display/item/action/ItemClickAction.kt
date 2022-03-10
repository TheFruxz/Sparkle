package de.jet.paper.tool.display.item.action

import de.jet.paper.tool.display.item.action.ItemActionType.CLICK
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = CLICK,
    override val executionProcess: suspend InventoryClickEvent.() -> Unit
) : ItemAction<InventoryClickEvent>