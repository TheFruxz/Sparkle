package de.jet.jvm.extension.data

import de.jet.jvm.extension.objects.trustOrThrow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder

internal val runningModuleModifications = mutableListOf<SerializersModuleBuilder.() -> Unit>()
internal var lastKnownModifications = listOf<SerializersModuleBuilder.() -> Unit>()

/**
 * This value returns the current [Json] from the cached value,
 * or creates a new one, if no Json exists, or its modifications
 * are outdated.
 * @sample toJson
 * @author Fruxz
 * @since 1.0
 */
@Suppress("JSON_FORMAT_REDUNDANT")
val jsonBase: Json
	get() {
		if (jsonBaseState == null || lastKnownModifications != runningModuleModifications) {
			Json {
				prettyPrint = true
				isLenient = true
				ignoreUnknownKeys = true
				coerceInputValues = true
				encodeDefaults = true
				explicitNulls = true
				allowStructuredMapKeys = true
				allowSpecialFloatingPointValues = true
				serializersModule = SerializersModule {
					runningModuleModifications.forEach {
						this.apply(it)
					}
				}
			}.let { constructed ->
				jsonBaseState = constructed
				return constructed
			}
		} else
			return jsonBaseState.trustOrThrow(IllegalStateException("Json state cannot be null"))
	}

/**
 * The current cached state of the json base,
 * can change, if modification-list updates!
 * @see jsonBase
 * @author Fruxz
 * @since 1.0
 */
private var jsonBaseState: Json? = null

/**
 * Adds a custom modification to the [SerializersModule] during its build process
 * for the [Json] object, used at the [toJson] and [fromJson] functions.
 * @param process the modification to the [SerializersModuleBuilder] in the building process
 * @author Fruxz
 * @since 1.0
 */
fun addJetJsonModification(process: SerializersModuleBuilder.() -> Unit) {
	runningModuleModifications += process
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