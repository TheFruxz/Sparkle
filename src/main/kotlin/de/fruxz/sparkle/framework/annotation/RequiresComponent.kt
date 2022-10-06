package de.fruxz.sparkle.framework.annotation

import de.fruxz.sparkle.framework.infrastructure.component.Component
import kotlin.reflect.KClass

@Repeatable
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class RequiresComponent(val module: KClass<out Component>) // TODO add to start blocks of interchanges, components and more, so that they can check if the given module is loaded
