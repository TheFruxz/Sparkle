package de.jet.minecraft.tool.display.item.quirk

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

interface Quirk {

	val itemStackProcessing: ItemStack.() -> Unit

	companion object {

		private fun <T : ItemMeta> processor(process: T.() -> Unit) = object : Quirk {
			@Suppress("UNCHECKED_CAST")
			override val itemStackProcessing: ItemStack.() -> Unit = {
				val modifiedMeta = itemMeta as T
				itemMeta = modifiedMeta.apply(process)
			}
		}

		val empty = object : Quirk {
			override val itemStackProcessing: ItemStack.() -> Unit =
				{ }
		}

		fun <T : ItemMeta> custom(process: T.() -> Unit) = processor(process)

		fun banner(process: BannerMeta.() -> Unit) = processor(process)

		fun book(process: BookMeta.() -> Unit) = processor(process)

		fun compass(process: CompassMeta.() -> Unit) = processor(process)

		fun crossbow(process: CrossbowMeta.() -> Unit) = processor(process)

		fun firework(process: FireworkMeta.() -> Unit) = processor(process)

		fun leatherArmor(process: LeatherArmorMeta.() -> Unit) = processor(process)

		fun map(process: MapMeta.() -> Unit) = processor(process)

		fun skull(process: SkullMeta.() -> Unit) = processor(process)

		fun potion(process: PotionMeta.() -> Unit) = processor(process)

		fun spawnEgg(process: SpawnEggMeta.() -> Unit) = processor(process)

	}

}