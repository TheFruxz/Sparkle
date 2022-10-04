package de.fruxz.sparkle.framework.visual.item.action

import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.visual.item.action.ItemActionType.CLICK
import org.bukkit.event.inventory.InventoryClickEvent

class ItemClickAction(
    override val identity: String,
    override val type: ItemActionType = CLICK,
    override val executionProcess: InventoryClickEvent.() -> Unit,
    override val created: Calendar = Calendar.now(),
) : ItemAction<InventoryClickEvent> {

    override fun register() { SparkleCache.itemActions += this }

    override fun unregister() { SparkleCache.itemActions -= this }

    override fun isRegistered() = SparkleCache.itemActions.contains(this)

}