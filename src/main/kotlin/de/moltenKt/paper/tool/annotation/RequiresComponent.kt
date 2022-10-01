package de.moltenKt.paper.tool.annotation

import de.moltenKt.paper.structure.component.Component
import kotlin.reflect.KClass

@Repeatable
@MustBeDocumented
annotation class RequiresComponent(val module: KClass<out Component>) // TODO add to start blocks of interchanges, components and more, so that they can check if the given module is loaded
