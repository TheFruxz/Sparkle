package dev.fruxz.sparkle.framework.ux.inventory.container

import net.kyori.adventure.text.ComponentLike
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

fun buildInventory(size: Int, owner: InventoryHolder? = null, process: Inventory.() -> Unit = { }) =
    Bukkit.createInventory(owner, size).apply(process)

fun buildInventory(size: Int, label: ComponentLike, owner: InventoryHolder? = null, process: Inventory.() -> Unit = { }) =
    Bukkit.createInventory(owner, size, label.asComponent()).apply(process)

fun buildInventory(type: InventoryType, owner: InventoryHolder? = null, process: Inventory.() -> Unit = { }) =
    Bukkit.createInventory(owner, type).apply(process)

fun buildInventory(type: InventoryType, label: ComponentLike, owner: InventoryHolder? = null, process: Inventory.() -> Unit = { }) =
    Bukkit.createInventory(owner, type, label.asComponent()).apply(process)