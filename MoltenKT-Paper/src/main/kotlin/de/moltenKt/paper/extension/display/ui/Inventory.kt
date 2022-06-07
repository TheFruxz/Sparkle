package de.moltenKt.paper.extension.display.ui

import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.paper.extension.paper.createKey
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.tool.display.item.ItemLike
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun buildInventory(size: Int, process: Inventory.() -> Unit) = Bukkit.createInventory(null, size).apply(process)

fun buildInventory(size: Int, label: TextComponent, process: Inventory.() -> Unit) = Bukkit.createInventory(null, size, label).apply(process)

operator fun <T : Inventory> T.get(slot: Int) = tryOrNull { getItem(slot) }

operator fun <T : Inventory> T.set(slot: Int, itemStack: ItemStack) = setItem(slot, itemStack)

operator fun <T : Inventory> T.set(slots: Iterable<Int>, itemStack: ItemStack) = slots.forEach { set(it, itemStack) }

operator fun <T : Inventory> T.set(slots: Iterable<Int>, process: (slot: Int) -> ItemStack) = slots.forEach { set(it, process(it)) }

operator fun <T : Inventory> T.set(slot: Int, itemLike: ItemLike) = setItem(slot, itemLike.produce())

operator fun <T : Inventory> T.set(slots: Iterable<Int>, itemLike: ItemLike) = slots.forEach { set(it, itemLike) }

operator fun <T : Inventory> T.set(slot: Int, material: Material) = setItem(slot, material.itemStack)

operator fun <T : Inventory> T.set(slots: Iterable<Int>, material: Material) = slots.forEach { set(it, material.itemStack) }

fun <T : Inventory> T.addItems(vararg items: ItemStack) = addItem(*items).toMap()

fun <T : Inventory> T.addItems(vararg items: ItemLike) = addItem(*items.map { it.produce() }.toTypedArray()).toMap()

fun <T : Inventory> T.addItems(vararg items: Material) = addItem(*items.map { it.itemStack }.toTypedArray()).toMap()

/**
 * Returns the slot id of the inventory, where the best center is located.
 * If the inventory does not have a center, a near-center slot is returned.
 * @author Fruxz
 * @since 1.0
 */
val Inventory.center: Int get() = (size / 2) - 1
