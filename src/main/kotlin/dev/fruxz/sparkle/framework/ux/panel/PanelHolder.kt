package dev.fruxz.sparkle.framework.ux.panel

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

data class PanelHolder(
    val panel: Panel,
) : InventoryHolder {
    private lateinit var inventoryState: Inventory

    override fun getInventory(): Inventory {
        if (!::inventoryState.isInitialized) inventoryState = panel.produce()

        return inventoryState
    }

}
