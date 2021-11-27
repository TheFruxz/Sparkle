package de.jet.jvm.extension.collection

/**
 * Replaces all occurrences of the given [Map.keys] with the given [Map.values] in this string.
 * @param map the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(map: Map<out Any?, Any?>): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("$key", "$value")
	}

	return out
}

/**
 * Replaces all occurrences of the given [Pair.first] with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(vararg pairs: Pair<Any?, Any?>) = replace(mapOf(*pairs))

/**
 * Replaces all occurrences of the given [Pair.first] with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(pairs: Collection<Pair<Any?, Any?>>) = replace(*pairs.toTypedArray())

/**
 * Replaces all occurrences of the given [Map.keys] surrounded by a `[` and a `]` with the given [Map.values] in this string.
 * @param map the map of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] surrounded by a `[` and a `]` replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(map: Map<out Any?, Any?>): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("[$key]", "$value")
	}

	return out
}

/**
 * Replaces all occurrences of the given [Pair.first] surrounded by a `[` and a `]` with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] surrounded by a `[` and a `]` replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(vararg pairs: Pair<Any?, Any?>) = replaceVariables(mapOf(*pairs))

/**
 * Replaces all occurrences of the given [Pair.first] surrounded by a `[` and a `]` with the given [Pair.second] in this string.
 * @param pairs the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Pair.first] surrounded by a `[` and a `]` replaced by the given [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(pairs: Collection<Pair<Any?, Any?>>) = replaceVariables(*pairs.toTypedArray())

/**
 * Generates a new complete empty String without any content or any characters.
 * @return a new empty String
 * @author Fruxz
 * @since 1.0
 */
fun emptyString() = ""

