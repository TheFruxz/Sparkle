package de.moltenKt.core.extension.classType

import de.moltenKt.core.extension.math.limitTo

/**
 * Returns the next enum in the ordinal order. If the current enum is the last one, the first
 * one is returned if [overflow] is true, otherwise it returns itself.
 * @param overflow if true, the first enum is returned if the current enum is the last one.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.next(overflow: Boolean = true): T =
	enumValues<T>()[
			if (overflow) {
				(ordinal + 1) % enumValues<T>().size
			} else {
				(ordinal + 1).limitTo(0..enumValues<T>().lastIndex)
			}]

/**
 * Returns the previous enum in the ordinal order. If the current enum is the first one, the last
 * one is returned if [overflow] is true, otherwise it returns itself.
 * @param overflow if true, the last enum is returned if the current enum is the first one.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previous(overflow: Boolean = true): T =

	if (overflow) {
		enumValues<T>()[(ordinal - 1).let { if (it < 0) enumValues<T>().lastIndex else it }]
	} else {
		enumValues<T>()[(ordinal - 1).limitTo(0..enumValues<T>().lastIndex)]
	}

/**
 * Returns the next enum in the ordinal order, or null, if this is already the last enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrNull(): T? = if (ordinal == enumValues<T>().lastIndex) null else next()

/**
 * Returns the previous enum in the ordinal order, or null, if this is already the first enum.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrNull(): T? = if (ordinal == 0) null else previous()

/**
 * Returns the next enum in the ordinal order, or if this enum is already the last enum, returns the first enum
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.nextOrFirst(): T = nextOrNull() ?: enumValues<T>().first()

/**
 * Returns the previous enum in the ordinal order, or if this enum is already the first enum, returns the last enum
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Enum<T>> Enum<T>.previousOrLast(): T = previousOrNull() ?: enumValues<T>().last()