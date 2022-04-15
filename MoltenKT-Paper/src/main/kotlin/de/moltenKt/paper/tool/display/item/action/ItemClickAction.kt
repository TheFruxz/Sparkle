package de.moltenKt.paper.tool.display.item.action

import de.moltenKt.jvm.extension.container.addIfNotContained
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.tool.display.item.action.ItemActionType.CLICK
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = CLICK,
    override val executionProcess: suspend InventoryClickEvent.() -> Unit
) : ItemAction<InventoryClickEvent> {

    override fun register() { MoltenCache.itemActions.addIfNotContained(this)}

    override fun unregister() { MoltenCache.itemActions.remove(this) }

    override fun isRegistered() = MoltenCache.itemActions.contains(this)

}