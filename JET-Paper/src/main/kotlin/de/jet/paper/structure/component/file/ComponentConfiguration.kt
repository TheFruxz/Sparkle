package de.jet.paper.structure.component.file

import de.jet.paper.app.JetApp
import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfiguration(
	var jetVersion: String = JetApp.instance.description.version,
	var components: List<ComponentConfigurationEntry> = listOf(),
)
