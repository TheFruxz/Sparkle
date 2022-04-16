package de.moltenKt.paper.app

import de.moltenKt.core.tool.data.Base64.decodeToString
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenData.File.CONFIG
import de.moltenKt.paper.app.MoltenData.File.ESSENTIALS_CONFIG
import de.moltenKt.paper.app.component.point.asset.PointConfig
import de.moltenKt.paper.extension.data.moltenPath
import de.moltenKt.paper.tool.data.DataTransformer
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.data.file.MoltenFileSystem
import java.util.*
import kotlin.io.path.div

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
		default = Locale.ENGLISH.language,
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

	object File {

		val CONFIG = MoltenYamlFile.generateYaml(MoltenFileSystem.rootPath() / "setup.yml")

		val ESSENTIALS_CONFIG = MoltenYamlFile.generateYaml(MoltenFileSystem.componentPath(Identifiable.custom("MoltenKT:World-Points")) / "points.yml")

	}

}