package de.fruxz.sparkle.app.component.ui.gui

import de.fruxz.ascend.extension.container.mapCast
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.display.ui.affectedItem
import de.fruxz.sparkle.extension.display.ui.item
import de.fruxz.sparkle.runtime.event.interact.PlayerInteractAtItemEvent
import de.fruxz.sparkle.structure.app.event.EventListener
import de.fruxz.sparkle.tool.display.item.action.ItemAction
import de.fruxz.sparkle.tool.display.item.action.ItemActionType
import kotlinx.coroutines.launch
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent

internal class ItemTagListener : EventListener() {

    private inline fun <reified T : Event> getAllActions(type: ItemActionType? = null) =
        SparkleCache.itemActions.filter { type == null || it.type == type }.mapCast<ItemAction<T>>()

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        val actions = getAllActions<InventoryClickEvent>(ItemActionType.CLICK)
        val item = event.affectedItem?.takeIf { it.hasItemMeta() }?.item

        if (item != null) {
            val itemActions = item.itemActionTags
            actions.forEach { action ->

                if (itemActions.any { it == action.registrationTag }) {

                    vendor.coroutineScope.launch {
                        action.executionProcess(event)
                    }

                }

            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInteract(event: PlayerInteractAtItemEvent) {
        val actions = getAllActions<PlayerInteractAtItemEvent>(ItemActionType.INTERACT)
        val item = event.item
        val itemActions = item.itemActionTags

        actions.forEach { action ->

            if (itemActions.any { it == action.registrationTag }) {

                action.executionProcess(event)

            }

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDrop(event: PlayerDropItemEvent) {
        val actions = getAllActions<PlayerDropItemEvent>(ItemActionType.DROP)
        val item = event.itemDrop.itemStack.item
        val itemActions = item.itemActionTags

        actions.forEach { action ->

            if (itemActions.any { it == action.registrationTag }) {

                vendor.coroutineScope.launch {
                    action.executionProcess(event)
                }

            }

        }
    }

}