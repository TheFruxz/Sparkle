package de.moltenKt.paper.tool.display.item.action

import de.moltenKt.jvm.extension.container.addIfNotContained
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.tool.display.item.action.ItemActionType.DROP
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val type: ItemActionType = DROP,
    override val executionProcess: suspend PlayerDropItemEvent.() -> Unit,
) : ItemAction<PlayerDropItemEvent> {

    override fun register() { MoltenCache.itemActions.addIfNotContained(this)}

    override fun unregister() { MoltenCache.itemActions.remove(this) }

    override fun isRegistered() = MoltenCache.itemActions.contains(this)

}