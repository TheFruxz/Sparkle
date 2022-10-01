package de.fruxz.sparkle.tool.display.item.action

import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.tool.display.item.action.ItemActionType.DROP
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