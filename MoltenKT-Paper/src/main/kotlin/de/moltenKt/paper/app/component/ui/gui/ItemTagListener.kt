package de.moltenKt.paper.app.component.ui.gui

import de.moltenKt.core.extension.container.mapCast
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.ui.affectedItem
import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.runtime.event.interact.PlayerInteractAtItemEvent
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.tool.display.item.action.ItemAction
import de.moltenKt.paper.tool.display.item.action.ItemActionType
import kotlinx.coroutines.launch
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent

internal class ItemTagListener : EventListener() {

    private inline fun <reified T : Event> getAllActions(type: ItemActionType? = null) =
        MoltenCache.itemActions.filter { type == null || it.type == type }.mapCast<ItemAction<T>>()

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