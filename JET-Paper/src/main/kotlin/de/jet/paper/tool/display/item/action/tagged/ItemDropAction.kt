package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val executionProcess: PlayerDropItemEvent.() -> Unit,
    override val type: ItemActionType = ItemActionType.DROP,
) : ItemAction<PlayerDropItemEvent> {

    init {
        JetCache.itemActions.addIfNotContained(this)
    }

}