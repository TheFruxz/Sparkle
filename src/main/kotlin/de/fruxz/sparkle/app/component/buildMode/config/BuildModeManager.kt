package de.fruxz.sparkle.app.component.buildMode.config

import de.fruxz.ascend.extension.data.fromJson
import de.fruxz.ascend.extension.data.readJson
import de.fruxz.ascend.extension.data.toJson
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.generateFileAndPath
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.tool.data.file.MoltenPath
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable.Companion
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import kotlin.io.path.readText
import kotlin.io.path.writeText

object BuildModeManager {

	private val path = MoltenPath.componentPath(KeyedIdentifiable.custom(system.subKey("build-mode", CONTINUE))) / "config.json"

	private var _state: BuildModeConfiguration? = null

	var state: BuildModeConfiguration
		get() = _state ?: tryOrNull { path.readJson() } ?: BuildModeConfiguration().also {
			path.toFile().generateFileAndPath()
			state = it
		}
		set(value) {
			_state = value
			path.toFile().generateFileAndPath()
			path.writeJson(value)
		}

}