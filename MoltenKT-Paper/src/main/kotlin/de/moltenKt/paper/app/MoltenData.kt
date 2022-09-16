package de.moltenKt.paper.app

import de.moltenKt.core.extension.data.fromJsonFileOrNull
import de.moltenKt.core.extension.data.writeJson
import de.moltenKt.paper.app.MoltenData.File.ESSENTIALS_CONFIG
import de.moltenKt.paper.app.component.point.asset.PointConfig
import de.moltenKt.paper.extension.data.moltenPath
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.tool.data.DataTransformer
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.data.file.MoltenPath
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.unfold.extension.KeyingStrategy.CONTINUE
import de.moltenKt.unfold.extension.subKey
import kotlinx.serialization.Serializable
import kotlin.io.path.createDirectories
import kotlin.io.path.div

object MoltenData {

	private var _systemConfig: MoltenConfig? = null

	private val systemConfigPath = MoltenPath.appPath(system) / "settings.json"

	var systemConfig: MoltenConfig
		get() = _systemConfig ?: systemConfigPath.fromJsonFileOrNull() ?: MoltenConfig().also {
			systemConfig = it
		}
		set(value) {
			_systemConfig = value
			systemConfigPath.parent.createDirectories()
			systemConfigPath.writeJson(value)
		}

	// ESSENTIALS component

	val savedPoints = Preference(
		file = ESSENTIALS_CONFIG,
		path = moltenPath("savedPoints"),
		default = PointConfig(emptyList()),
	).transformer(DataTransformer.json())

	object File {

		val ESSENTIALS_CONFIG = MoltenYamlFile.generateYaml(MoltenPath.componentPath(KeyedIdentifiable.custom(system.subKey("world-points", CONTINUE))) / "points.yml")

	}

	@Serializable
	data class MoltenConfig(
		val debugMode: Boolean = false,
		val httpClientCaching: Boolean = false,
		val prefix: Map<String, String> = mapOf(
			"prefix.general" to "<dark_gray>⏵ ",
			"prefix.fail" to "<#FF5A00>✘ <dark_gray>⏵ ",
			"prefix.error" to "<#FF0000>☄ <dark_gray>⏵ ",
			"prefix.level" to "<#FF00FE>" + "\uD83D\uDDE1 <dark_gray>⏵ ",
			"prefix.warning" to "<#F3FF00>⚐ <dark_gray>⏵ ",
			"prefix.attention" to "<#FFC900>⏳ <dark_gray>⏵ ",
			"prefix.payment" to "<#8600FF>₿ <dark_gray>⏵ ",
			"prefix.applied" to "<#00FF65>✎ <dark_gray>⏵ ",
			"prefix.process" to "<#4200db>⋯ <dark_gray>⏵ ",
		)
	)

}