package de.jet.minecraft.app.component.essentials.world

import de.jet.library.extension.tag.PromisingData
import kotlinx.serialization.Serializable

@Serializable
data class WorldConfig(
	var importedWorlds: List<String>,
	var autostartWorlds: List<String>,
) : PromisingData
