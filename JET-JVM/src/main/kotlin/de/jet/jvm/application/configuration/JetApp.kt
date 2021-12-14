package de.jet.jvm.application.configuration

import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import de.jet.jvm.tool.smart.identification.Identifiable

/**
 * This class represents the raw data of a JET application.
 * @param identity is the unique identifier of the application, used to manage configurations and resources.
 * @param version is the version of the application.
 * @author Fruxz
 * @since 1.0
 */
open class JetApp(
	override val identity: String,
	open val version: Version = 1.0.version,
) : Identifiable<JetApp>
