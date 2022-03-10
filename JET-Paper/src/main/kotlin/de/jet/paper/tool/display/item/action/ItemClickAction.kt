package de.jet.paper.tool.display.item.action

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import de.jet.paper.tool.display.item.action.ItemActionType.CLICK
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = CLICK,
    override val executionProcess: suspend InventoryClickEvent.() -> Unit
) : ItemAction<InventoryClickEvent> {

    override fun register() { JetCache.itemActions.addIfNotContained(this)}

    override fun unregister() { JetCache.itemActions.remove(this) }

    override fun isRegistered() = JetCache.itemActions.contains(this)

}