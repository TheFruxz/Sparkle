package de.jet.jvm.application.configuration

import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class represents the configuration of JET.
 * This configuration contains the version of the JET,
 * that created the configuration file. (compatibility)
 * and also a list of [JetAppConfigModule]s.
 * @param jetVersion the JET-instance, that created the configuration file.
 * @param apps the list of [JetAppConfigModule]s, used/configured to run with JET.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("appConfiguration")
data class JetAppConfiguration(
	val jetVersion: Version = 1.0.version,
	val apps: List<JetAppConfigModule>,
)
