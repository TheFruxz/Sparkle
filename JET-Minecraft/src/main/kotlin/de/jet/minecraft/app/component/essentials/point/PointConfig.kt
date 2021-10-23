package de.jet.minecraft.app.component.essentials.point

import de.jet.library.extension.tag.PromisingData
import kotlinx.serialization.Serializable

@Serializable
data class PointConfig(
	val points: List<Point>,
) : PromisingData
