package de.jet.jvm.application.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("version")
data class Version(
	val versionNumber: Double,
)

val Number.version: Version
	get() = Version(toDouble())