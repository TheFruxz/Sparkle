package dev.fruxz.sparkle.framework.ux.inventory.container

import dev.fruxz.sparkle.framework.ux.inventory.item.*
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

// direct give

fun HumanEntity.giveItems(vararg itemStack: ItemStack) {
    inventory.addItem(*itemStack)
}

fun HumanEntity.giveItems(vararg itemLike: ItemLike) {
    inventory.addItem(*itemLike)
}

// additional inventory stuff

fun Inventory.addItem(vararg itemLike: ItemLike) {
    addItem(*itemLike.map { it.asItemStack() }.toTypedArray())
}

fun Inventory.addItem(vararg material: Material) {
    addItem(*material.map { it.itemStack }.toTypedArray())
}

// operator functions

operator fun Inventory.get(index: Int): ItemStack? =
    getItem(index)

operator fun Inventory.get(indexes: Iterable<Int>): List<ItemStack?> =
    indexes.map { getItem(it) }

fun Inventory.getItemOrNull(index: Int): Item? =
    this[index]?.item

fun Inventory.getItemsOrNull(indexes: Iterable<Int>): List<Item?> =
    this[indexes].map { it?.item }

fun Inventory.getItems(indexes: Iterable<Int>): List<Item> =
    this[indexes].mapNotNull { it?.item }

fun Inventory.getItemsOrNull(vararg indexes: Int): List<Item?> =
    this[indexes.toList()].map { it?.item }

fun Inventory.getItems(vararg indexes: Int): List<Item> =
    this[indexes.toList()].mapNotNull { it?.item }

operator fun Inventory.set(index: Int, itemStack: ItemStack) {
    setItem(index, itemStack)
}

operator fun Inventory.set(index: Int, itemLike: ItemLike) {
    setItem(index, itemLike.asItemStack())
}

operator fun Inventory.set(indexes: Iterable<Int>, itemStack: ItemStack) {
    indexes.forEach { setItem(it, itemStack) }
}

operator fun Inventory.set(indexes: Iterable<Int>, itemLike: ItemLike) {
    indexes.forEach { setItem(it, itemLike.asItemStack()) }
}

operator fun PlayerInventory.set(slot: EquipmentSlot, itemStack: ItemStack) {
    setItem(slot, itemStack)
}

operator fun PlayerInventory.set(slot: EquipmentSlot, itemLike: ItemLike) {
    setItem(slot, itemLike.asItemStack())
}

// properties

var PlayerInventory.mainHand: ItemLike
    get() = itemInMainHand.itemLike
    set(value) = setItemInMainHand(value.asItemStack())

var PlayerInventory.offHand: ItemLike
    get() = itemInOffHand.itemLike
    set(value) = setItemInOffHand(value.asItemStack())

val Inventory.slots: IntRange
    get() = 0 until size

val Inventory.center: Int
    get() = (size / 2) - 1
