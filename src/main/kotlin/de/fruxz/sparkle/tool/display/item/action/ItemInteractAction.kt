package de.fruxz.sparkle.tool.display.item.action

import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.runtime.event.interact.PlayerInteractAtItemEvent
import de.fruxz.sparkle.tool.display.item.action.ItemActionType.INTERACT

class ItemInteractAction(
    override val identity: String,
    override val type: ItemActionType = INTERACT,
    override val executionProcess: PlayerInteractAtItemEvent.() -> Unit,
    override val created: Calendar = Calendar.now(),
) : ItemAction<PlayerInteractAtItemEvent> {

    override fun register() { SparkleCache.itemActions += this }

    override fun unregister() { SparkleCache.itemActions -= this }

    override fun isRegistered() = SparkleCache.itemActions.contains(this)

}