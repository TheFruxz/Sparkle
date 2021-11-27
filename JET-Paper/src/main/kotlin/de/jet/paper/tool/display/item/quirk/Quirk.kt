package de.jet.paper.tool.display.item.quirk

import de.jet.paper.tool.display.item.Item
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

		fun <T : ItemMeta> Item.customQuirk(process: T.() -> Unit) = putQuirk(custom(process))

		fun banner(process: BannerMeta.() -> Unit) = processor(process)

		fun Item.bannerQuirk(process: BannerMeta.() -> Unit) = putQuirk(banner(process))

		fun book(process: BookMeta.() -> Unit) = processor(process)

		fun Item.bookQuirk(process: BookMeta.() -> Unit) = putQuirk(book(process))

		fun compass(process: CompassMeta.() -> Unit) = processor(process)

		fun Item.compassQuirk(process: CompassMeta.() -> Unit) = putQuirk(compass(process))

		fun crossbow(process: CrossbowMeta.() -> Unit) = processor(process)

		fun Item.crossbowQuirk(process: CrossbowMeta.() -> Unit) = putQuirk(crossbow(process))

		fun firework(process: FireworkMeta.() -> Unit) = processor(process)

		fun Item.fireworkQuirk(process: FireworkMeta.() -> Unit) = putQuirk(firework(process))

		fun leatherArmor(process: LeatherArmorMeta.() -> Unit) = processor(process)

		fun Item.leatherArmorQuirk(process: LeatherArmorMeta.() -> Unit) = putQuirk(leatherArmor(process))

		fun map(process: MapMeta.() -> Unit) = processor(process)

		fun Item.mapQuirk(process: MapMeta.() -> Unit) = putQuirk(map(process))

		fun skull(process: SkullMeta.() -> Unit) = processor(process)

		fun Item.skullQuirk(process: SkullMeta.() -> Unit) = putQuirk(skull(process))

		fun potion(process: PotionMeta.() -> Unit) = processor(process)

		fun Item.potionQuirk(process: PotionMeta.() -> Unit) = putQuirk(potion(process))

		fun spawnEgg(process: SpawnEggMeta.() -> Unit) = processor(process)

		fun Item.spawnEggQuirk(process: SpawnEggMeta.() -> Unit) = putQuirk(spawnEgg(process))

	}

}