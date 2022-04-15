package de.moltenKt.paper.tool.annotation

import de.moltenKt.paper.structure.component.Component
import kotlin.reflect.KClass

@Repeatable
@MustBeDocumented
internal annotation class RequiresComponent(val module: KClass<out Component>)
