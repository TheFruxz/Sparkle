package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.sparkle.framework.system.itemFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

// Material -> ?

val Material.itemStack: ItemStack
    get() = ItemStack(this)

fun Material.itemStack(process: ItemStack.() -> Unit) =
    itemStack.apply(process)

val Material.item: Item
    get() = Item(this)

fun Material.item(process: Item.() -> Unit) =
    item.apply(process)

val Material.itemMeta: ItemMeta?
    get() = itemFactory.getItemMeta(this)

fun Material.itemMeta(process: ItemMeta.() -> Unit) =
    itemMeta?.apply(process)

// ItemStack -> ?

val ItemStack.item: Item
    get() = Item(this)

fun ItemStack.item(process: Item.() -> Unit) =
    item.apply(process)

val ItemStack.itemMetaOrNull: ItemMeta?
    get() = this.itemMeta ?: this.type.itemMeta

fun ItemStack.itemMetaOrNull(process: ItemMeta.() -> Unit) =
    itemMetaOrNull?.apply(process)

val ItemStack.itemLike: ItemLike
    get() = ItemLike.of(this)

fun ItemStack.itemLike(process: ItemLike.() -> Unit) =
    itemLike.apply(process)