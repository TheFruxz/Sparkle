package de.jet.paper.tool.annotation

import de.jet.paper.structure.component.Component
import kotlin.reflect.KClass

@Repeatable
@MustBeDocumented
internal annotation class RequiresComponent(val module: KClass<out Component>)
