package de.jet.paper.tool.display.item.action

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.paper.app.JetCache
import de.jet.paper.tool.display.item.action.ItemActionType.DROP
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val type: ItemActionType = DROP,
    override val executionProcess: suspend PlayerDropItemEvent.() -> Unit,
) : ItemAction<PlayerDropItemEvent> {

    override fun register() { JetCache.itemActions.addIfNotContained(this)}

    override fun unregister() { JetCache.itemActions.remove(this) }

    override fun isRegistered() = JetCache.itemActions.contains(this)

}