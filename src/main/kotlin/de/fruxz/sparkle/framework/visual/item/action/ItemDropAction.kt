package de.fruxz.sparkle.framework.visual.item.action

import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.visual.item.action.ItemActionType.DROP
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropAction(
    override val identity: String,
    override val type: ItemActionType = DROP,
    override val executionProcess: PlayerDropItemEvent.() -> Unit,
    override val created: Calendar = Calendar.now(),
) : ItemAction<PlayerDropItemEvent> {

    override fun register() { SparkleCache.itemActions += this }

    override fun unregister() { SparkleCache.itemActions -= this }

    override fun isRegistered() = SparkleCache.itemActions.contains(this)

}