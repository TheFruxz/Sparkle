package de.moltenKt.paper.app.old_component.essentials.world

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("EssentialsWorldConfig")
data class WorldConfig(
	var importedWorlds: List<String>,
	var autostartWorlds: List<String>,
)
