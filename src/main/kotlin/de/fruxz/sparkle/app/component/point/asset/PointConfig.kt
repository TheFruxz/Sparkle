package de.fruxz.sparkle.app.component.point.asset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("EssentialsPointConfig")
data class PointConfig(
	val points: List<Point>,
)
