package de.fruxz.sparkle.framework.visual.item

import de.fruxz.ascend.tool.smart.composition.Producible
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.visual.ui.itemStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * This interface is used to get into the item-based approach
 * of items inside Sparkle for third-party apps.
 * Third-party item-systems should implement this interface,
 * to be able to use their items with Sparkle seamlessly.
 * @author Fruxz
 * @since 1.0
 */
interface ItemLike : Producible<ItemStack> {

	/**
	 * This function produces an Sparkle [Item]
	 * based on the given [ItemLike] object.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun asItem(): Item

	/**
	 * This function produces an Bukkit [ItemStack]
	 * based on the given [ItemLike] object.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun asItemStack(): ItemStack

	/**
	 * This function utilizes the [ItemLike.asItemStack] function
	 * to replace the [Producible.produce] function.
	 * This is used to make the [ItemLike] object usable
	 * to more api-compatible systems, that do not directly uses
	 * [ItemLike] input parameters, but accept [Producible] of
	 * type [ItemStack]! (For example third-party inventory-frameworks)
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun produce(): ItemStack =
		asItemStack()

	companion object {

		/**
		 * This function wraps the [itemStack] into an [ItemLike] object.
		 * If you want to use this in your system, I suggest you to use
		 * the **ItemStack.itemLike** computational value!
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun of(itemStack: ItemStack) = object : ItemLike {

			override fun asItem() = itemStack.item

			override fun asItemStack() = itemStack

		}

		/**
		 * This function wraps the [material] into an [ItemLike] object.
		 * If you want to use this in your system, I suggest you to use
		 * the **Material.itemLike** computational value!
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun of(material: Material) = object : ItemLike {

			override fun asItem() = material.item

			override fun asItemStack() = material.itemStack

		}

	}

}