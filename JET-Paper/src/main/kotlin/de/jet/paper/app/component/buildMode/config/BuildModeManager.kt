package de.jet.paper.app.component.buildMode.config

import de.jet.jvm.extension.data.fromJson
import de.jet.jvm.extension.data.toJson
import de.jet.jvm.extension.div
import de.jet.jvm.extension.generateFileAndPath
import de.jet.jvm.extension.tryOrNull
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.tool.data.JetYamlFile
import kotlin.io.path.readText
import kotlin.io.path.writeText

object BuildModeManager {

	private val path = JetYamlFile.componentPath(Identifiable.custom("JET:BuildMode")) / "config.json"

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