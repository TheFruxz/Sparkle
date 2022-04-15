package de.moltenKt.paper.app

import de.moltenKt.jvm.tool.data.Base64.decodeToString
import de.moltenKt.paper.app.MoltenData.File.CONFIG
import de.moltenKt.paper.app.MoltenData.File.ESSENTIALS_CONFIG
import de.moltenKt.paper.app.MoltenData.File.ESSENTIALS_WORLDS
import de.moltenKt.paper.app.component.point.asset.PointConfig
import de.moltenKt.paper.app.old_component.essentials.world.WorldConfig
import de.moltenKt.paper.app.old_component.essentials.world.tree.WorldRenderer
import de.moltenKt.paper.extension.data.moltenPath
import de.moltenKt.paper.tool.data.DataTransformer
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.Preference

object MoltenData {

	val debugMode = Preference(
		file = CONFIG,
		useCache = true,
		path = moltenPath("debugMode"),
		default = false,
	)

	val systemPrefix = Preference(
		file = CONFIG,
		useCache = true,
		path = moltenPath("prefix"),
		default = "<gold>MoltenKT <dark_gray>» ",
	).transformer(DataTransformer.simpleColorCode())

	val systemLanguage = Preference(
		file = CONFIG,
		path = moltenPath("language"),
		default = "en_general",
	)

	// SKULLS

	@Suppress("SpellCheckingInspection")
	val skullDataURL = Preference(
		file = CONFIG,
		path = moltenPath("skullData"),
		default = decodeToString(
			decodeToString(
				decodeToString(
					"WVVoU01HTklUVFpNZVRsMFlWYzFiRmt6U21oYWJsRjBZVWRXYUZwSVRYVlpNamwwVERKT2VtUnBPSGxOUkVsM1RGUkJlRXhVVFhoTVZXeFdXakZLYVZOdE9VbFZiVXBYWVVkd1RHSnJPWE5oTWpGSlREQk9NV016VW5aaVV6RkpXbGRHYTB4VlVrTk1iVTU2WkdjOVBRPT0="
				)
			)
		),
	)

	// ESSENTIALS component

	val savedPoints = Preference(
		file = ESSENTIALS_CONFIG,
		path = moltenPath("savedPoints"),
		default = PointConfig(emptyList()),
	).transformer(DataTransformer.json())

	val worldConfig = Preference(
		file = ESSENTIALS_WORLDS,
		path = moltenPath("worldConfig"),
		default = WorldConfig(emptyList(), emptyList()),
	).transformer(DataTransformer.json())

	val worldStructure = Preference(
		file = ESSENTIALS_WORLDS,
		path = moltenPath("worldStructure"),
		default = WorldRenderer.renderBase(worldConfig.content).structure(),
	).transformer(DataTransformer.json())

	object File {

		val CONFIG = MoltenYamlFile.rootFile("system-config")

		val ESSENTIALS_CONFIG = MoltenYamlFile.dummyComponentFile("Essentials", "MoltenKT", "config")
		val ESSENTIALS_WORLDS = MoltenYamlFile.dummyComponentFile("Essentials", "MoltenKT", "worlds")

	}

}