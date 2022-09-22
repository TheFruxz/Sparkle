package de.moltenKt.core.extension.container

import kotlin.collections.Map.Entry

/**
 * This function transforms a [Pair] out of 2 collections into
 * a map with its keys and values. Using the [first] of each
 * element as the key of the MapEntry and the [second] as the
 * value of the MapEntry.
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Pair<Collection<F>, Collection<S>>.toMap() = first.zip(second).toMap()

/**
 * This function returns the copy of [this] [Map],
 * but the keys are now the values and the values
 * are now the keys.
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.flipped() = (values to keys).toMap()

/**
 * Returns the first [Map.Entry] matching the given [predicate] or
 * null if no such element exists.
 * @param predicate the [predicate] to match
 * @return the first [Map.Entry] matching the given [predicate] or null
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.firstOrNull(predicate: (Entry<F, S>) -> Boolean): Entry<F, S>? {

	forEach { mapEntry ->
		if (predicate(mapEntry)) return mapEntry
	}

	return null
}

/**
 * Returns the first [Map.Entry] matching the given [predicate] or
 * throws an [NoSuchElementException] if no such element exists.
 * @param predicate the [predicate] to match
 * @return the first [Map.Entry] matching the given [predicate]
 * @throws NoSuchElementException if no element matches the given [predicate]
 * @author Fruxz
 * @since 1.0
 */
fun <F, S> Map<F, S>.first(predicate: (Entry<F, S>) -> Boolean): Entry<F, S> =
	firstOrNull(predicate) ?: throw NoSuchElementException("No element found for the given predicate")

/**
 * Returns the last [Map.Entry] matching the given [predicate] or null if no such element exists.
 * @param predicate the predicate to match
 * @return the last [Map.Entry] matching the given [predicate] or null
 * @author Fruxz
 * @since 1.0
 */
fun <K, V> Map<K, V>.lastOrNull(predicate: (Entry<K, V>) -> Boolean): Entry<K, V>? {

	forEach { mapEntry ->
		if (predicate(mapEntry)) return mapEntry
	}

	return null
}

/**
 * Returns the last [Map.Entry] matching the given [predicate] or throws an [NoSuchElementException] if no such element exists
 * @param predicate the predicate to match
 * @return the last [Map.Entry] matching the given [predicate]
 * @throws NoSuchElementException if no element matches the given [predicate]
 * @author Fruxz
 * @since 1.0
 */
fun <K, V> Map<K, V>.last(predicate: (Entry<K, V>) -> Boolean): Entry<K, V> =
	lastOrNull(predicate) ?: throw NoSuchElementException("No element found for the given predicate")
