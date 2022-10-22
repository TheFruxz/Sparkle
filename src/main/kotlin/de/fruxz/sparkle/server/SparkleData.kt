package de.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.fromJsonFileOrNull
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.extension.sparkle
import kotlinx.serialization.Serializable
import kotlin.io.path.createDirectories
import kotlin.io.path.div

object SparkleData {

	private var _systemConfig: SparkleConfig? = null

	private val systemConfigPath = SparklePath.appPath(sparkle) / "settings.json"

	var systemConfig: SparkleConfig
		get() = _systemConfig ?: systemConfigPath.fromJsonFileOrNull() ?: SparkleConfig().also {
			systemConfig = it
		}
		set(value) {
			_systemConfig = value
			systemConfigPath.parent.createDirectories()
			systemConfigPath.writeJson(value)
		}

	@Serializable
	data class SparkleConfig(
		val debugMode: Boolean = false,
		val httpClientCaching: Boolean = false,
		val entriesPerListPage: Int = 9,
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