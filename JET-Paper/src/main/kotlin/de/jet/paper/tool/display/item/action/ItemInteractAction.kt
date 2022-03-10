package de.jet.paper.tool.display.item.action

import de.jet.paper.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.paper.tool.display.item.action.ItemActionType.INTERACT

class ItemInteractAction(
    override val identity: String,
    override val type: ItemActionType = INTERACT,
    override val executionProcess: suspend PlayerInteractAtItemEvent.() -> Unit,
) : ItemAction<PlayerInteractAtItemEvent>