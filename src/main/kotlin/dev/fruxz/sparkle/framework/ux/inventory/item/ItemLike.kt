package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.ascend.tool.smart.composition.Producible
import org.bukkit.inventory.ItemStack

interface ItemLike : Producible<ItemStack> {

    fun asItem(): Item

    fun asItemStack(): ItemStack

    override fun produce(): ItemStack =
        asItemStack()

    companion object {

        // TODO
//        /**
//         * This function wraps the [itemStack] into an [ItemLike] object.
//         * If you want to use this in your system, I suggest you to use
//         * the **ItemStack.itemLike** computational value!
//         * @author Fruxz
//         * @since 1.0
//         */
//        @JvmStatic
//        fun of(itemStack: ItemStack) = object : ItemLike {
//
//            override fun asItem() = itemStack.item
//
//            override fun asItemStack() = itemStack
//
//        }
//
//        /**
//         * This function wraps the [material] into an [ItemLike] object.
//         * If you want to use this in your system, I suggest you to use
//         * the **Material.itemLike** computational value!
//         * @author Fruxz
//         * @since 1.0
//         */
//        @JvmStatic
//        fun of(material: Material) = object : ItemLike {
//
//            override fun asItem() = material.item
//
//            override fun asItemStack() = material.itemStack
//
//        }

    }

}