package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import de.jet.paper.runtime.event.interact.PlayerInteractAtItemEvent

class ItemInteractAction(
    override val identity: String,
    override val executionProcess: PlayerInteractAtItemEvent.() -> Unit,
    override val type: ItemActionType = ItemActionType.INTERACT,
) : ItemAction<PlayerInteractAtItemEvent> {

    init {
        JetCache.itemActions.addIfNotContained(this)
    }

}