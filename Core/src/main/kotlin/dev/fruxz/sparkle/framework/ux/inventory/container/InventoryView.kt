package dev.fruxz.sparkle.framework.ux.inventory.container

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

/**
 * Returns the list of [InventoryView]s that are currently viewing this [Inventory].
 * This is determined by the [Inventory.getViewers], which are filtered by the
 * [InventoryView.getTopInventory] being this [Inventory].
 * @author Fruxz
 * @since 1.0
 */
val Inventory.views: List<InventoryView>
    get() = this.viewers.mapNotNull {
        it.openInventory.takeIf { openView ->
            openView.topInventory == this
        }
    }