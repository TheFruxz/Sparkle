package de.jet.jvm.tool.mutable

/**
 * This open class is a mutable flexible implementation of the [Mutable]<[T]> interface.
 * @param T The type of the mutable flexible.
 * @param property The property of the mutable flexible.
 * @author Fruxz
 * @since 1.0
 */
open class FlexibleMutable<T>(override var property: T) : Mutable<T>
