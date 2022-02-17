package de.jet.paper.extension.paper

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.extension.interchange.InterchangeExecutor

val InterchangeExecutor.identityObject: Identity<InterchangeExecutor>
	get() = Identity(name)