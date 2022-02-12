package de.jet.paper.app.old_component.essentials.point

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("EssentialsPointConfig")
data class PointConfig(
	val points: List<Point>,
)
