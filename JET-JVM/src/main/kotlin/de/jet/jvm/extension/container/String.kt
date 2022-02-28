package de.jet.jvm.extension.container

import java.util.*

/**
 * Replaces all occurrences of the given [Map.keys] with the given [Map.values] in this string.
 * @param map the pairs of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replace(map: Map<out Any?, Any?>, ignoreCase: Boolean = false): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("$key", "$value", ignoreCase)
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
fun String.replace(pairs: Collection<Pair<Any?, Any?>>, ignoreCase: Boolean = false) = replace(map = pairs.toMap(), ignoreCase = ignoreCase)

/**
 * Replaces all occurrences of the given [Map.keys] surrounded by a `[` and a `]` with the given [Map.values] in this string.
 * @param map the map of keys and values to replace
 * @return the string with all occurrences of the given [Map.keys] surrounded by a `[` and a `]` replaced by the given [Map.values]
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceVariables(map: Map<out Any?, Any?>, ignoreCase: Boolean = false): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("[$key]", "$value", ignoreCase)
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
fun String.replaceVariables(pairs: Collection<Pair<Any?, Any?>>, ignoreCase: Boolean = false) = replaceVariables(pairs.toMap(), ignoreCase)

/**
 * Generates a new complete empty String without any content or any characters.
 * @return a new empty String
 * @author Fruxz
 * @since 1.0
 */
fun emptyString() = ""

/**
 * This function uses the [UUID.fromString] function to generate the
 * UUID from the given [String] and returns it.
 * @throws IllegalArgumentException if the given [String] is not a valid UUID
 * @return the UUID generated from the given [String]
 * @author Fruxz
 * @since 1.0
 */
@Throws(IllegalArgumentException::class)
fun String.toUUID() = UUID.fromString(this)!!

/**
 * Replaces the first [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [startsWith] the [oldValue].
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @see replaceFirst
 * @author Fruxz
 * @since 1.0
 */
fun String.replacePrefix(oldValue: String, newValue: String, ignoreCase: Boolean = false) = if (startsWith(oldValue, ignoreCase)) {
	replaceFirst(oldValue, newValue, ignoreCase)
} else
	this

/**
 * Replaces the last [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [endsWith] the [oldValue].
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceSuffix(oldValue: String, newValue: String, ignoreCase: Boolean = false) = if (endsWith(oldValue, ignoreCase)) {
	split(oldValue).dropLast(1).joinToString(oldValue) + newValue
} else
	this

/**
 * Replaces the first and last [oldValue] with the [newValue] (respecting the [ignoreCase]),
 * if the String [startsWith] or/and [endsWith] the [oldValue].
 * If only one side (start or end) starts or ends with the [oldValue],
 * then only this will get replaced, but there is no error, that the other
 * side is not found!
 * @param oldValue the text which gets replaced
 * @param newValue the text which gets placed instead
 * @param ignoreCase if the case is ignored at check & replace
 * @return the replaced string
 * @see replacePrefix
 * @see replaceSuffix
 * @author Fruxz
 * @since 1.0
 */
fun String.replaceSurrounding(oldValue: String, newValue: String, ignoreCase: Boolean = false) =
	replacePrefix(oldValue, newValue, ignoreCase)
		.replaceSuffix(oldValue, newValue, ignoreCase)
