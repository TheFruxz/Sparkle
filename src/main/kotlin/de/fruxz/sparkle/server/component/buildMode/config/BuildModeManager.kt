package de.fruxz.sparkle.server.component.buildMode.config

import de.fruxz.ascend.extension.data.readJson
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.generateFileAndPath
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.util.data.file.SparklePath
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.identification.KeyedIdentifiable
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey

object BuildModeManager {

	private val path = SparklePath.componentPath(KeyedIdentifiable.custom(system.subKey("build-mode", CONTINUE))) / "config.json"

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