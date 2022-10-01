package de.fruxz.sparkle.structure.component.file

import de.fruxz.sparkle.app.SparkleApp
import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfiguration(
	var sparkleVersion: String = SparkleApp.instance.description.version,
	var components: List<ComponentConfigurationEntry> = listOf(),
)
