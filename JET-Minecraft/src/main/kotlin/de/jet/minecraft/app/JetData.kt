package de.jet.minecraft.app

import de.jet.library.extension.data.jetPath
import de.jet.library.tool.conversion.Base64.decodeToString
import de.jet.minecraft.app.JetData.File.BRAIN
import de.jet.minecraft.app.JetData.File.CONFIG
import de.jet.minecraft.app.JetData.File.ESSENTIALS_CONFIG
import de.jet.minecraft.app.JetData.File.ESSENTIALS_WORLDS
import de.jet.minecraft.app.component.essentials.point.PointConfig
import de.jet.minecraft.app.component.essentials.world.WorldConfig
import de.jet.minecraft.app.component.essentials.world.tree.WorldTree
import de.jet.minecraft.tool.data.DataTransformer
import de.jet.minecraft.tool.data.JetFile
import de.jet.minecraft.tool.data.Preference

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

	// SKULLS

	val skullDataURL = Preference(
		file = CONFIG,
		path = jetPath("skullData"),
		default = decodeToString(decodeToString(decodeToString(
			"WVVoU01HTklUVFpNZVRsMFlWYzFiRmt6U21oYWJsRjBZVWRXYUZwSVRYVlpNamwwVERKT2VtUnBPSGxOUkVsM1RGUkJlRXhVVFhoTVZXeFdXakZLYVZOdE9VbFZiVXBYWVVkd1RHSnJPWE5oTWpGSlREQk9NV016VW5aaVV6RkpXbGRHYTB4VlVrTk1iVTU2WkdjOVBRPT0="
		))),
	)

	// ESSENTIALS component

	val savedPoints = Preference(
		file = ESSENTIALS_CONFIG,
		path = jetPath("savedPoints"),
		default = PointConfig(emptyList()),
	).transformer(DataTransformer.jsonObject())

	val worldConfig = Preference(
		file = ESSENTIALS_WORLDS,
		path = jetPath("worldConfig"),
		default = WorldConfig(emptyList(), emptyList()),
	).transformer(DataTransformer.jsonObject())

	val worldStructure = Preference(
		file = ESSENTIALS_WORLDS,
		path = jetPath("worldStructure"),
		default = WorldTree.renderBase(worldConfig.content).structure(),
	).transformer(DataTransformer.jsonRenderObject())

	object File {

		val CONFIG = JetFile.rootFile("system-config")
		val BRAIN = JetFile.rootFile("system-memory")
		val ESSENTIALS_CONFIG = JetFile.dummyComponentFile("Essentials", "JET", "config")
		val ESSENTIALS_WORLDS = JetFile.dummyComponentFile("Essentials", "JET", "worlds")
		val TESTING = JetFile.rootFile("system-testing")

	}

}