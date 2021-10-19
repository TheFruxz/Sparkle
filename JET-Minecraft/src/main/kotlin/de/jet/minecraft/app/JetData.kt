package de.jet.minecraft.app

import de.jet.library.extension.data.jetPath
import de.jet.minecraft.app.JetData.File.BRAIN
import de.jet.minecraft.app.JetData.File.CONFIG
import de.jet.minecraft.app.JetData.File.ESSENTIALS
import de.jet.minecraft.app.component.essentials.point.PointingData
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

	val savedPoints = Preference(
		file = ESSENTIALS,
		path = jetPath("savedPoints"),
		default = PointingData(emptyList())
	).transformer(DataTransformer.jsonObject())

	object File {

		val CONFIG = JetFile.rootFile("system-config")
		val BRAIN = JetFile.rootFile("system-memory")
		val ESSENTIALS = JetFile.dummyComponentFile("Essentials", "JET", "config")
		val TESTING = JetFile.rootFile("system-testing")

	}

}