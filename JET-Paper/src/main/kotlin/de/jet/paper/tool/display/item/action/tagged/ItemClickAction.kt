package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = ItemActionType.CLICK,
    override val executionProcess: InventoryClickEvent.() -> Unit
) : ItemAction<InventoryClickEvent> {

    init {
        JetCache.itemActions.addIfNotContained(this)
    }

}