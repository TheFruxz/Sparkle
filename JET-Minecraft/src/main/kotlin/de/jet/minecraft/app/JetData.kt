package de.jet.minecraft.app

import de.jet.library.extension.data.jetPath
import de.jet.minecraft.app.JetData.File.BRAIN
import de.jet.minecraft.app.JetData.File.CONFIG
import de.jet.minecraft.app.JetData.File.TESTING
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.tool.data.DataTransformer
import de.jet.minecraft.tool.data.JetFile
import de.jet.minecraft.tool.data.Preference
import de.jet.minecraft.tool.display.item.Modification
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES
import org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS

object JetData {

	val debugMode = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("debugMode"),
		default = false,
	)

	val systemPrefix = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("prefix"),
		default = "§6JET §8» ",
	).transformer(DataTransformer.simpleColorCode())

	val systemLanguage = Preference(
		file = CONFIG,
		path = jetPath("language"),
		default = "en_general",
	)

	val autoStartComponents = Preference(
		file = CONFIG,
		path = jetPath("autoStartComponents"),
		default = setOf<String>(),
	).transformer(DataTransformer.setCollection())

	val touchedComponents = Preference(
		file = BRAIN,
		path = jetPath("touchedComponents"),
		default = setOf<String>()
	).transformer(DataTransformer.setCollection())

	val item = Preference(
		file = TESTING,
		path = jetPath("item"),
		default = Material.GRAVEL.item.apply {
			label = "test"
			lore = """
				test1
				test2
				test3
				test4
			""".trimIndent()
			annexModifications(Modification(Enchantment.FIRE_ASPECT, 20), Modification(Enchantment.DAMAGE_ALL, 5), Modification(Enchantment.SWEEPING_EDGE, 2))
			annexFlags(HIDE_ATTRIBUTES, HIDE_ENCHANTS)
		}
	).transformer(DataTransformer.jsonItem())

	object File {

		val CONFIG = JetFile.rootFile("system-config")
		val BRAIN = JetFile.rootFile("system-memory")
		val TESTING = JetFile.rootFile("system-testing")

	}

}