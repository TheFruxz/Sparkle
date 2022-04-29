package de.moltenKt.paper.tool.display.item

import de.moltenKt.core.tool.smart.Producible
import org.bukkit.inventory.ItemStack

/**
 * This interface is used to get into the item-based approach
 * of items inside MoltenKT for third-party apps.
 * Third-party item-systems should implement this interface,
 * to be able to use their items with MoltenKT seamlessly.
 * @author Fruxz
 * @since 1.0
 */
interface ItemLike : Producible<ItemStack> {

	/**
	 * This function produces an MoltenKT-Paper [Item]
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
	 * This function utelizes the [ItemLike.asItemStack] function
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

}