package de.jet.minecraft.app.component.essentials.point

import de.jet.library.extension.tag.Data
import kotlinx.serialization.Serializable

@Serializable
data class PointingData(
	val points: List<Point>,
) : Data
