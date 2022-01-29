package de.jet.paper.tool.annotation

import de.jet.paper.structure.component.Component
import kotlin.RequiresOptIn.Level.WARNING
import kotlin.reflect.KClass

@RequiresOptIn("""
	This Class/Function/Feature requires a specific on-runtime enabled component running!
	Opt-In this annotation, if you want to use this feature and actually care about, that
	the required component is running, to avoid exceptions and other critical issues.
""", WARNING)
@Repeatable
internal annotation class RequiresComponent(val module: KClass<out Component>)
