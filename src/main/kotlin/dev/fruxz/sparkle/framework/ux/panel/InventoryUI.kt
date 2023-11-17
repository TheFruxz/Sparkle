package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.ascend.tool.smart.generate.producible.Producible
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

interface InventoryUI: Producible<Inventory> {

    override fun produce(): Inventory

    fun display(vararg receivers: Player) {
        receivers.forEach { it.openInventory(produce()) }
    }

}