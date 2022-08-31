package de.moltenKt.paper.app.component.buildMode.config

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.div
import de.moltenKt.core.extension.generateFileAndPath
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.tool.data.file.MoltenPath
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.paper.tool.smart.KeyedIdentifiable.Companion
import de.moltenKt.unfold.extension.KeyingStrategy.CONTINUE
import de.moltenKt.unfold.extension.subKey
import net.kyori.adventure.key.Key
import kotlin.io.path.readText
import kotlin.io.path.writeText

object BuildModeManager {

	private val path = MoltenPath.componentPath(KeyedIdentifiable.custom(system.subKey("build-mode", CONTINUE))) / "config.json"

	private var _state: BuildModeConfiguration? = null

	var state: BuildModeConfiguration
		get() = _state ?: tryOrNull { path.readText().fromJson() } ?: BuildModeConfiguration().also {
			path.toFile().generateFileAndPath()
			state = it
		}
		set(value) {
			_state = value
			path.toFile().generateFileAndPath()
			path.writeText(value.toJson())
		}

}