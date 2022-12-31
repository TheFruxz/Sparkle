package de.fruxz.sparkle.framework.annotation

import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.infrastructure.Attachable
import de.fruxz.sparkle.framework.infrastructure.component.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

@Repeatable
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class RequiresComponent(val requiredComponent: KClass<out Component>) { // TODO add to start blocks of interchanges, components and more, so that they can check if the given module is loaded

    companion object {

        fun Attachable.requirementsMet() = this::class.findAnnotations<RequiresComponent>().all { annotation ->
            sparkle.appCache.runningComponents.any { it::class == annotation.requiredComponent }
        }

        fun Attachable.requiredComponents() = this::class.findAnnotations<RequiresComponent>().map { it.requiredComponent }

        fun Attachable.missingComponents() = requiredComponents() - sparkle.appCache.runningComponents.map { it::class }.toSet()

    }

}
