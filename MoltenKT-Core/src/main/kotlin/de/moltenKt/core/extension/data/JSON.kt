package de.moltenKt.core.extension.data

import de.moltenKt.core.extension.dump
import de.moltenKt.core.extension.readTextOrNull
import de.moltenKt.core.extension.tryOrNull
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.file.OpenOption
import java.nio.file.Path
import kotlin.DeprecationLevel.WARNING
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
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

// toJson conversion

/**
 * Tries to encode the given object to a JSON string using the Kotlinx
 * serialization library's [Json.encodeToString] function.
 * @return The JSON string.
 * @author Fruxz
 * @since 1.0
 */
@Deprecated(level = WARNING, message = "use toJsonString() instead", replaceWith = ReplaceWith("this.toJsonString()"))
inline fun <reified T : Any> T.toJson() = toJsonString()

/**
 * This function converts [this] object to a json string via the [jsonBase]
 * and [Json.encodeToString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToString
 * @return The object represented as a JSON string.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonString() = jsonBase.encodeToString(this)

/**
 * This function tries to return the result of executing the [toJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonString
 * @return The object represented as a JSON string, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStringOrNull() = tryOrNull { toJsonString() }

/**
 * This function converts [this] object as a json stream into the [stream] via the
 * [jsonBase] and [Json.encodeToStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToStream
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStream(stream: OutputStream) = jsonBase.encodeToStream(this, stream)

/**
 * This function tries to return the result of executing the [toJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonStream
 * @return The unit if it succeeded, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStreamOrNull(stream: OutputStream) = tryOrNull { toJsonStream(stream) }

/**
 * This function converts [this] object to a [JsonElement] via the [jsonBase]
 * and [Json.encodeToJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToJsonElement
 * @return The object represented as a [JsonElement].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElement() = jsonBase.encodeToJsonElement(this)

/**
 * This function tries to return the result of executing the [toJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonElement
 * @return The object represented as a [JsonElement], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElementOrNull() = tryOrNull { toJsonElement() }

// fromJson conversion

/**
 * Tries to decode the given JSON string to an object type [T] using the
 * Kotlinx serialization library's [Json.decodeFromString] function.
 * @param T The result type, which is the destination type.
 * @return The decoded object.
 * @author Fruxz
 * @since 1.0
 */
@Deprecated(level = WARNING, message = "use fromJsonString() instead", replaceWith = ReplaceWith("this.fromJsonString<T>()"))
inline fun <reified T : Any> String.fromJson() = fromJsonString<T>()

/**
 * This function converts [this] JSON string to an object type [T] via the [jsonBase]
 * and [Json.decodeFromString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromString
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonString() = jsonBase.decodeFromString<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see fromJsonString
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonStringOrNull() = tryOrNull { fromJsonString<T>() }

/**
 * This function converts [this] JSON stream to an object type [T] via the [jsonBase]
 * and [Json.decodeFromStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromStream
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStream() = jsonBase.decodeFromStream<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see InputStream.fromJsonStream
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStreamOrNull() = tryOrNull { fromJsonStream<T>() }

/**
 * This function converts [this] [JsonElement] to an object type [T] via the [jsonBase]
 * and [Json.decodeFromJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElement() = jsonBase.decodeFromJsonElement<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see JsonElement.fromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElementOrNull() = tryOrNull { fromJsonElement<T>() }

/**
 * This function reads the content of [this] Path using the [readText] function and
 * converts the content from a String to the requested [T] object via the [fromJsonStream]
 * function.
 * This process can throw exceptions if something goes wrong!
 * @see Path.readText
 * @see fromJsonString
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.fromJsonFile(charset: Charset = Charsets.UTF_8) = readText(charset).fromJsonString<T>()

/**
 * This function tries to return the result of executing the [fromJsonFile] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see Path.fromJsonFile
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.fromJsonFileOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { fromJsonFile<T>(charset) }

/**
 * This function reads the content of [this] File using the [readText] function and
 * converts the content from a String to the requested [T] object via the [fromJsonString].
 * This process can throw exceptions if something goes wrong!
 * @see File.readText
 * @see fromJsonString
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.fromJsonFile(charset: Charset = Charsets.UTF_8) = readText(charset).fromJsonString<T>()

/**
 * This function tries to return the result of executing the [fromJsonFile] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see File.fromJsonFile
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.fromJsonFileOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { fromJsonFile<T>(charset) }

// write JSON

/**
 * This function writes the given [this] object to a JSON file via the [jsonBase]
 * and [toJsonString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonString
 * @see writeText
 * @return The path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJson(content: T, charset: Charset = Charsets.UTF_8, vararg options: OpenOption) = apply { writeText(content.toJsonString(), charset, *options) }

/**
 * This function writes the given [this] object to a JSON file via the [jsonBase]
 * and [toJsonString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonString
 * @see writeText
 * @return The file itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJson(content: T, charset: Charset = Charsets.UTF_8) = apply { writeText(content.toJsonString(), charset) }

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfNotExists(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, vararg options: OpenOption) = apply {
	if (!exists()) {
		if (createParent) parent.createDirectories()
		writeJson(content, charset, *options)
	}
}

/**
 * This function writes the given [content], to [this] file , via the [writeJson]
 * if the file currently does not exist.
 * @param content the content to write as json to the file
 * @param createParent if the parent directories will be created during the write
 * @param charset the charset to use fo r to write
 * @see writeJson
 * @return the file itself
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJsonIfNotExists(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8) = apply {
	if (!exists()) {
		if (createParent) parentFile.mkdirs()
		writeJson(content, charset)
	}
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently is empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfEmpty(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, vararg options: OpenOption) = apply {
	if (readTextOrNull(charset)?.isBlank() != false) {
		if (createParent) parent.createDirectories()
		writeJson(content, charset, *options)
	}
}

/**
 * This function writes the given [content], to [this] file, via the [writeJson]
 * if [this] file is currently empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the file itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJsonIfEmpty(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8) = apply {
	if (readTextOrNull(charset)?.isBlank() != false) {
		if (createParent) parentFile.mkdirs()
		writeJson(content, charset)
	}
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path is currently blank or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfBlank(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, vararg options: OpenOption) = apply {
	if (readTextOrNull(charset)?.isBlank() != false) {
		if (createParent) parent.createDirectories()
		writeJson(content, charset, *options)
	}
}

inline fun <reified T> File.writeJsonIfBlank(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8) = apply {
	if (readTextOrNull(charset)?.isBlank() != false) {
		if (createParent) parentFile.mkdirs()
		writeJson(content, charset)
	}
}


// read JSON

/**
 * This function returns the content of [this] Path using the [fromJsonFile] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJson(charset: Charset = Charsets.UTF_8) = fromJsonFile<T>(charset)

/**
 * This function returns the content of [this] File using the [fromJsonFileOrNull] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJsonOrNull(charset: Charset = Charsets.UTF_8) = fromJsonFileOrNull<T>(charset)

/**
 * This function returns the content of [this] File using the [fromJsonFile] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJson(charset: Charset = Charsets.UTF_8) = fromJsonFile<T>()

/**
 * This function returns the content of [this] File using the [fromJsonFileOrNull] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJsonOrNull(charset: Charset = Charsets.UTF_8) = fromJsonFileOrNull<T>()

// read default

/**
 * This function reads the content of the file under [this] Path, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created during the write.
 * @param charset the charset to use for to write
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, charset: Charset = Charsets.UTF_8, vararg options: OpenOption) = if (writeIfBlank) {
	readJsonOrNull<T>(charset) ?: default.also {
		if (writeCreatesParent) parent.createDirectories()
		writeJson(default, charset, *options)
	}
} else {
	readJsonOrNull<T>(charset) ?: default
}

/**
 * This function reads the content of [this] file, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created during the write.
 * @param charset the charset to use for to write
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, charset: Charset = Charsets.UTF_8) = if (writeIfBlank) {
	readJsonOrNull<T>(charset) ?: default.also {
		if (writeCreatesParent) parentFile.mkdirs()
		writeJson(default, charset)
	}
} else {
	readJsonOrNull<T>(charset) ?: default
}