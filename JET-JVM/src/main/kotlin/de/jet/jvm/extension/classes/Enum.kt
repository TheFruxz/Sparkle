package de.jet.jvm.extension.classes

import de.jet.jvm.extension.math.limitTo

/**
 * Returns the next enum in the ordinal order, or itself, if it is already the last enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.next() = enumValues<T>()[(ordinal + 1).limitTo(0..enumValues<T>().lastIndex)]

/**
 * Returns the previous enum in the ordinal order, or itself, if it is already the first enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previous() = enumValues<T>()[(ordinal - 1).limitTo(0..enumValues<T>().lastIndex)]