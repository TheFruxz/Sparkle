package de.moltenKt.core.extension.data

import de.moltenKt.core.extension.dump
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlin.reflect.KClass

internal val runningJsonModuleModifications = mutableListOf<SerializersModuleBuilder.() -> Unit>()
internal var lastKnownJsonModuleModifications = listOf<SerializersModuleBuilder.() -> Unit>()

internal val runningJsonModifications = mutableListOf<JsonBuilder.() -> Unit>()
internal var lastKnownJsonModifications = listOf<JsonBuilder.() -> Unit>()

internal var contextuals = setOf<SerializersModule>()
internal var contextualUpdate = false

/**
 * This value returns the current [Json] from the cached value,
 * or creates a new one, if no Json exists, or its modifications
 * are outdated.
 * @sample toJson
 * @author Fruxz
 * @since 1.0
 */
@Suppress("JSON_FORMAT_REDUNDANT")
var jsonBase: Json
	get() {
		if (backingJsonBase == null
			|| lastKnownJsonModuleModifications != runningJsonModuleModifications
			|| lastKnownJsonModifications != runningJsonModifications
			|| contextualUpdate
		) {
			println("Json is updating...")
			contextualUpdate = false
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
					contextuals.forEach { contextual ->
						include(contextual)
					}
					runningJsonModuleModifications.forEach {
						this.apply(it)
					}
				}
				runningJsonModifications.forEach {
					this.apply(it)
				}
			}.let { constructed ->
				backingJsonBase = constructed
				return constructed
			}
		} else
			return backingJsonBase!!
	}
	set(value) { backingJsonBase = value }

/**
 * The current cached state of the json base,
 * can change, if modification-list updates!
 * @see jsonBase
 * @author Fruxz
 * @since 1.0
 */
private var backingJsonBase: Json? = null

/**
 * Adds a custom modification to the [SerializersModule] during its build process
 * for the [Json] object, used at the [toJson] and [fromJson] functions.
 * @param process the modification to the [SerializersModuleBuilder] in the building process
 * @author Fruxz
 * @since 1.0
 */
fun addMoltenJsonModuleModification(process: SerializersModuleBuilder.() -> Unit) {
	runningJsonModuleModifications += process
}

fun <T : Any> addJsonContextualConfiguration(clazz: KClass<T>, serializer: KSerializer<T>) {
	contextuals += SerializersModule {
		contextual(clazz, serializer)
	}
	contextualUpdate = true
	println("calling....")
	jsonBase.dump() // trigger update of module
}

/**
 * Adds a custom modification to the [Json] during its build process
 * for the [jsonBase], used at the [toJson] and [fromJson] functions.
 * @param process the modification to the [JsonBuilder] in the building process
 * @author Fruxz
 * @since 1.0
 */
fun addMoltenJsonModification(process: JsonBuilder.() -> Unit) {
	runningJsonModifications += process
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