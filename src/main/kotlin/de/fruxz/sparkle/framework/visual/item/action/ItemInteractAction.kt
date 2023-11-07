package de.fruxz.sparkle.framework.visual.item.action

import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.event.interact.PlayerInteractAtItemEvent
import de.fruxz.sparkle.framework.visual.item.action.ItemActionType.INTERACT
import de.fruxz.sparkle.server.SparkleCache

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