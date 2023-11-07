package de.fruxz.sparkle.framework.extension

import dev.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleCache
import net.kyori.adventure.key.Key
import kotlin.reflect.KClass

/**
 * This function returns the current registered component of the
 * [kClass] class, by using [Component.getInstance], or null, if
 * the component is not registered.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Component> componentOrNull(kClass: KClass<T>) = Component.getInstance<T>(kClass)

/**
 * This function returns the current registered component of the
 * [key] key, by using [SparkleCache.registeredComponents], or null, if
 * the component is not registered.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Component> componentOrNull(key: Key) = SparkleCache.registeredComponents.firstOrNull { it.key == key && it is T }

/**
 * This function returns the current registered component of the
 * [kClass] class, by using [Component.getInstance], or throws an
 * [IllegalArgumentException], if the component is not registered.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Component> component(kClass: KClass<T>) = componentOrNull<T>(kClass) ?: throw NoSuchElementException("The component with the class '${kClass.simpleName}' is not registered!")

/**
 * This function returns the current registered component of the
 * [key] key, by using [SparkleCache.registeredComponents], or throws an
 * [IllegalArgumentException], if the component is not registered.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Component> component(key: Key) = componentOrNull<T>(key)?.takeIfInstance<T>() ?: throw NoSuchElementException("The component with the key '$key' is not registered!")