package de.moltenKt.paper.app.component.buildMode.config

import de.moltenKt.jvm.extension.data.fromJson
import de.moltenKt.jvm.extension.data.toJson
import de.moltenKt.jvm.extension.div
import de.moltenKt.jvm.extension.generateFileAndPath
import de.moltenKt.jvm.extension.tryOrNull
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.file.MoltenFileSystem
import kotlin.io.path.readText
import kotlin.io.path.writeText

object BuildModeManager {

	private val path = MoltenFileSystem.componentPath(Identifiable.custom("MoltenKT:BuildMode")) / "config.json"

	private var _state: BuildModeConfiguration? = null

	var state: BuildModeConfiguration
		get() = _state ?: tryOrNull<BuildModeManager, BuildModeConfiguration> { path.readText().fromJson() }.also { println("test: ${it?.toJson()}") } ?: BuildModeConfiguration().also {
			path.toFile().generateFileAndPath()
			state = it
		}
		set(value) {
			_state = value
			path.toFile().generateFileAndPath()
			path.writeText(value.toJson())
		}

}