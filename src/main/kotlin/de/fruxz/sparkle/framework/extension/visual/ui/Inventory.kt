package de.fruxz.sparkle.framework.extension.visual.ui

import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.visual.canvas.CanvasBase
import de.fruxz.sparkle.framework.visual.item.ItemLike
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

fun buildInventory(size: Int, process: Inventory.() -> Unit) = Bukkit.createInventory(null, size).apply(process)

fun buildInventory(size: Int, label: TextComponent, process: Inventory.() -> Unit) = Bukkit.createInventory(null, size, label).apply(process)

fun buildInventory(canvasBase: CanvasBase, label: TextComponent? = null, owner: InventoryHolder? = null) = canvasBase.generateInventory(owner, label)

operator fun <T : Inventory> T.get(slot: Int) = tryOrNull { getItem(slot) }

operator fun <T : Inventory> T.set(slot: Int, itemStack: ItemStack?) = setItem(slot, itemStack)

operator fun <T : Inventory> T.set(slots: Iterable<Int>, itemStack: ItemStack?) = slots.forEach { set(it, itemStack) }

operator fun <T : Inventory> T.set(slots: Iterable<Int>, process: (slot: Int) -> ItemStack?) = slots.forEach { set(it, process(it)) }

operator fun <T : Inventory> T.set(slot: Int, itemLike: ItemLike?) = setItem(slot, itemLike?.produce())

operator fun <T : Inventory> T.set(slots: Iterable<Int>, itemLike: ItemLike?) = slots.forEach { set(it, itemLike) }

operator fun <T : Inventory> T.set(slot: Int, material: Material?) = setItem(slot, material?.itemStack)

operator fun <T : Inventory> T.set(slots: Iterable<Int>, material: Material?) = slots.forEach { set(it, material?.itemStack) }

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

/**
 * This computational value returns the [InventoryClickEvent.getCurrentItem] or
 * if this is null, the [InventoryClickEvent.getCursor] item.
 * This should lead to the situation, that any time an item is touched (even if
 * only laid into the inventory), the affected item is returned.
 * Why? Because [InventoryClickEvent.getCurrentItem] is NOT returning the item
 * in every case expected!
 * @author Fruxz
 * @since 1.0
 */
val InventoryClickEvent.affectedItem: ItemStack?
	get() = currentItem ?: cursor

var PlayerInventory.mainHand: ItemLike
	get() = itemInMainHand.itemLike
	set(value) = setItemInMainHand(value.asItemStack())

var PlayerInventory.offHand: ItemLike
	get() = itemInOffHand.itemLike
	set(value) = setItemInOffHand(value.asItemStack())