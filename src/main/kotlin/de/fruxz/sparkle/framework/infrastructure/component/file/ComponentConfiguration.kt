package de.fruxz.sparkle.framework.infrastructure.component.file

import de.fruxz.sparkle.server.SparkleApp
import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfiguration(
	var sparkleVersion: String = SparkleApp.instance.description.version,
	var components: List<ComponentConfigurationEntry> = listOf(),
)
