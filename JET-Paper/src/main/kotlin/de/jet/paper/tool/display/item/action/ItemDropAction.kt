package de.jet.paper.tool.display.item.action

import de.jet.paper.tool.display.item.action.ItemActionType.DROP
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val type: ItemActionType = DROP,
    override val executionProcess: suspend PlayerDropItemEvent.() -> Unit,
) : ItemAction<PlayerDropItemEvent>