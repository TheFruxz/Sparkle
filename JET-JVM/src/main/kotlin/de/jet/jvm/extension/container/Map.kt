package de.jet.jvm.extension.container

import kotlin.collections.Map.Entry

/**
 * This function transforms a [Pair] out of 2 collections into
 * a map with its keys and values. Using the [first] of each
 * element as the key of the MapEntry and the [second] as the
 * value of the MapEntry.
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Pair<Collection<F>, Collection<S>>.toMap() = first.withIndex().associate { it.value to second.get(it.index) }

/**
 * This function returns the copy of [this] [Map],
 * but the keys are now the values and the values
 * are now the keys.
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.flipped() = (values to keys).toMap()

/**
 * Returns the first [Map.Entry] matching the given [check] or
 * null if no such element exists.
 * @param check the [check] to match
 * @return the first [Map.Entry] matching the given [check] or null
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.firstOrNull(check: (Entry<F, S>) -> Boolean): Entry<F, S>? {
	forEach { mapEntry ->
		if (check(mapEntry)) return mapEntry
	}
	return null
}

/**
 * Returns the first [Map.Entry] matching the given [check] or
 * throws an [IllegalArgumentException] if no such element exists.
 * @param check the [check] to match
 * @return the first [Map.Entry] matching the given [check]
 * @throws IllegalArgumentException if no element matches the given [check]
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.first(check: (Entry<F, S>) -> Boolean): Entry<F, S> =
	firstOrNull(check) ?: throw NoSuchElementException("No element found for the given check")