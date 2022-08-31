package de.moltenKt.paper.app

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenData.File.ESSENTIALS_CONFIG
import de.moltenKt.paper.app.component.point.asset.PointConfig
import de.moltenKt.paper.extension.data.moltenPath
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.tool.data.DataTransformer
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.data.file.MoltenPath
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.paper.tool.smart.KeyedIdentifiable.Companion
import de.moltenKt.unfold.extension.KeyingStrategy.CONTINUE
import de.moltenKt.unfold.extension.subKey
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.readText
import kotlin.io.path.writeText

object MoltenData {

	private var _systemConfig: MoltenConfig? = null

	private val systemConfigPath = MoltenPath.appPath(system) / "settings.json"

	var systemConfig: MoltenConfig
		get() = _systemConfig ?: tryOrNull { systemConfigPath.readText().fromJson() } ?: MoltenConfig().also {
			systemConfig = it
		}
		set(value) {
			_systemConfig = value
			systemConfigPath.parent.createDirectories()
			systemConfigPath.writeText(value.toJson())
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
		val prefix: String = "<gold>MoltenKT <dark_gray>» ",
		val language: String = Locale.ENGLISH.language
	)

}