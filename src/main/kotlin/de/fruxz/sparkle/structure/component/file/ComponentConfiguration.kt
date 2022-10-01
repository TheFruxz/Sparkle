package de.fruxz.sparkle.structure.component.file

import de.fruxz.sparkle.app.MoltenApp
import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfiguration(
	var moltenVersion: String = de.fruxz.sparkle.app.MoltenApp.instance.description.version,
	var components: List<ComponentConfigurationEntry> = listOf(),
)
