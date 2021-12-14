package de.jet.jvm.application.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a version, based on a versionNumber, type: [Double]
 * @param versionNumber the internal version number as [Double]
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("version")
data class Version(
	val versionNumber: Double,
)

/**
 * Converts a number to a version, based on a versionNumber, type: [Double]
 * @author Fruxz
 * @since 1.0
 */
val Number.version: Version
	get() = Version(toDouble())