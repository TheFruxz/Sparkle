package de.jet.jvm.application.configuration

import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("appConfiguration")
data class JetAppConfiguration(
	val jetVersion: Version = 1.0.version,
	val apps: List<JetAppConfigModule>,
)
