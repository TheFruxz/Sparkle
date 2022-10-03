package de.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.fromJsonFileOrNull
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.sparkle.server.SparkleData.File.ESSENTIALS_CONFIG
import de.fruxz.sparkle.server.component.point.asset.PointConfig
import de.fruxz.sparkle.framework.util.data.DataTransformer
import de.fruxz.sparkle.framework.util.data.yaml.SparkleYamlFile
import de.fruxz.sparkle.framework.util.data.Preference
import de.fruxz.sparkle.framework.util.data.file.SparklePath
import de.fruxz.sparkle.framework.util.extension.data.sparklePath
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.identification.KeyedIdentifiable
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey
import kotlinx.serialization.Serializable
import kotlin.io.path.createDirectories
import kotlin.io.path.div

object SparkleData {

	private var _systemConfig: SparkleConfig? = null

	private val systemConfigPath = SparklePath.appPath(system) / "settings.json"

	var systemConfig: SparkleConfig
		get() = _systemConfig ?: systemConfigPath.fromJsonFileOrNull() ?: SparkleConfig().also {
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
		path = sparklePath("savedPoints"),
		default = PointConfig(emptyList()),
	).transformer(DataTransformer.json())

	object File {

		val ESSENTIALS_CONFIG = SparkleYamlFile.generateYaml(SparklePath.componentPath(KeyedIdentifiable.custom(system.subKey("world-points", CONTINUE))) / "points.yml")

	}

	@Serializable
	data class SparkleConfig(
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