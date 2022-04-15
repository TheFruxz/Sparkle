package de.moltenKt.paper.structure.component.file

import de.moltenKt.paper.app.MoltenApp
import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfiguration(
	var moltenVersion: String = MoltenApp.instance.description.version,
	var components: List<ComponentConfigurationEntry> = listOf(),
)
