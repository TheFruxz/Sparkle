package de.jet.jvm.extension.container

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
