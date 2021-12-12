package de.jet.jvm.application.configuration

import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import de.jet.jvm.tool.smart.identification.Identifiable

open class JetApp(
	override val identity: String,
	open val version: Version = 1.0.version,
) : Identifiable<JetApp>
