package de.jet.paper.app.component.linking

import de.jet.jvm.extension.container.mapCast
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.ui.item
import de.jet.paper.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.tool.display.item.action.ItemAction
import de.jet.paper.tool.display.item.action.ItemActionType
import kotlinx.coroutines.launch
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent

internal class ItemTagListener : EventListener() {

    private inline fun <reified T : Event> getAllActions(type: ItemActionType? = null) =
        JetCache.itemActions.filter { type == null || it.type == type }.mapCast<ItemAction<T>>()

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        val actions = getAllActions<InventoryClickEvent>(ItemActionType.CLICK)
        val item = event.currentItem?.takeIf { it.hasItemMeta() }?.item ?: event.cursor?.takeIf { it.hasItemMeta() }?.item

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

                vendor.coroutineScope.launch {
                    action.executionProcess(event)
                }

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