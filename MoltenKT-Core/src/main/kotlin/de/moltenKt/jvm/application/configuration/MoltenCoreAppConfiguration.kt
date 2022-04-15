package de.moltenKt.jvm.application.configuration

import de.moltenKt.jvm.application.tag.Version
import de.moltenKt.jvm.application.tag.version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class represents the configuration of MoltenKT.
 * This configuration contains the version of the MoltenKT,
 * that created the configuration file. (compatibility)
 * and also a list of [MoltenCoreAppConfigModule]s.
 * @param moltenVersion the MoltenKT-instance, that created the configuration file.
 * @param apps the list of [MoltenCoreAppConfigModule]s, used/configured to run with MoltenKT.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("appConfiguration")
data class MoltenCoreAppConfiguration(
	val moltenVersion: Version = 1.0.version,
	val apps: List<MoltenCoreAppConfigModule>,
)
