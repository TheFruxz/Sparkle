package de.moltenKt.paper.tool.display.item.action

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.tool.display.item.action.ItemActionType.CLICK
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = CLICK,
    override val executionProcess: InventoryClickEvent.() -> Unit,
    override val created: Calendar = Calendar.now(),
) : ItemAction<InventoryClickEvent> {

    override fun register() { MoltenCache.itemActions += this }

    override fun unregister() { MoltenCache.itemActions -= this }

    override fun isRegistered() = MoltenCache.itemActions.contains(this)

}