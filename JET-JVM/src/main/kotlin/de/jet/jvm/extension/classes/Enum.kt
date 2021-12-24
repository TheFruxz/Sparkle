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

/**
 * Returns the next enum in the ordinal order, or null, if this is already the last enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrNull() = if (ordinal == enumValues<T>().lastIndex) null else next()

/**
 * Returns the previous enum in the ordinal order, or null, if this is already the first enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrNull() = if (ordinal == 0) null else previous()

/**
 * Returns the next enum in the ordinal order, or if this enum is already the last enum, returns the first enum
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrFirst() = nextOrNull() ?: enumValues<T>().first()

/**
 * Returns the previous enum in the ordinal order, or if this enum is already the first enum, returns the last enum
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrLast() = previousOrNull() ?: enumValues<T>().last()