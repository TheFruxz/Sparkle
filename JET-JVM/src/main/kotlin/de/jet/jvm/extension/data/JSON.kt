package de.jet.jvm.extension.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val jsonBase = Json {
	prettyPrint = true
	isLenient = true
	ignoreUnknownKeys = true
	coerceInputValues = true
	encodeDefaults = true
	explicitNulls = true
	allowStructuredMapKeys = true
	allowSpecialFloatingPointValues = true
}

/**
 * Tries to encode the given object to a JSON string using the Kotlinx
 * serialization library's [Json.encodeToString] function.
 * @return The JSON string.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Any> T.toJson() = jsonBase.encodeToString(this)

/**
 * Tries to decode the given JSON string to an object type [T] using the
 * Kotlinx serialization library's [Json.decodeFromString] function.
 * @param T The result type, which is the destination type.
 * @return The decoded object.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Any> String.fromJson() = jsonBase.decodeFromString<T>(this)