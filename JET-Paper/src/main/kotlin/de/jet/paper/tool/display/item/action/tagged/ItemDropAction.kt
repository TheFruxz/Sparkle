package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val type: ItemActionType = ItemActionType.DROP,
    override val executionProcess: PlayerDropItemEvent.() -> Unit,
) : ItemAction<PlayerDropItemEvent> {

    init {
        JetCache.itemActions.addIfNotContained(this)
    }

}