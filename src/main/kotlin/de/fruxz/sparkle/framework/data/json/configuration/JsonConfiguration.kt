package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.extension.createFileAndDirectories
import de.fruxz.ascend.extension.data.fromJsonStringOrNull
import de.fruxz.ascend.extension.data.toJsonString
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.data.json.JsonConfiguration
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import kotlinx.serialization.json.JsonPrimitive
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.reflect.KProperty

data class Configuration<T>(
	val set: (T?) -> Unit,
	val get: () -> T,
)

@JvmInline
value class NewPreference<T>(val configuration: Configuration<T>) {

	init {
		configuration.set(configuration.get())
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = configuration.get()

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = configuration.set(value)

}

inline fun <reified T : Any> preference(
	app: SparkleApp,
	key: String,
	file: Path = SparklePath.appPath(app) / "preferences.json",
	crossinline defaultValue: () -> T
) = preference(file, key, defaultValue)

inline fun <reified T : Any> preference(
	component: Component,
	key: String,
	file: Path = SparklePath.componentPath(component) / "preferences.json",
	crossinline defaultValue: () -> T,
) = preference(file, key, defaultValue)

inline fun <reified T : Any> preference(
	file: Path,
	key: String,
	crossinline defaultValue: () -> T,
): NewPreference<T> = NewPreference(Configuration(
	set = { value ->
		file.absolute().let {
			if (it.notExists()) it.createFileAndDirectories()

			file.writeText(
				(file.readText().fromJsonStringOrNull<JsonConfiguration>() ?: JsonConfiguration()).let { config ->
					config.copy(elements = config.elements.let {
						if (value == null) it else (it + (key to value.toString().let {
							it.toBooleanStrictOrNull()?.let { JsonPrimitive(it) } ?:
							it.takeIf { !it.contains(".") && !it.endsWith("d", true) && !it.endsWith("f", true) }?.toIntOrNull()?.let { JsonPrimitive(it) } ?:
							it.toDoubleOrNull()?.let { JsonPrimitive(it) } ?:
							JsonPrimitive(it)
						}))
					})
				}.toJsonString()
			)

			value?.let { it1 -> preferenceCache.put(file to key, it1) } ?: preferenceCache.remove(file to key)

		}
	},
	get = {
		  file.absolute().let {
			  preferenceCache[file to key]?.forceCastOrNull<T>() ?: run {
				  if (it.notExists()) it.createFileAndDirectories()

				  file.readText().fromJsonStringOrNull<JsonConfiguration>()?.elements?.get(key)?.content?.let {
					  when {
						  T::class == Boolean::class -> it.toBooleanStrictOrNull().forceCastOrNull<T>()
						  T::class == Int::class -> it.toIntOrNull().forceCastOrNull<T>()
						  T::class == Double::class -> it.toDoubleOrNull().forceCastOrNull<T>()
						  T::class == Float::class -> it.toFloatOrNull().forceCastOrNull<T>()
						  T::class == String::class -> it.forceCastOrNull<T>()
						  else -> error("Unsupported type ${T::class}, must be primitive type, like Boolean, Int, Double or String")
					  }
				  } ?: run {
					  val value = defaultValue()
					  preferenceCache[file to key] = value
					  value
				  }

			  }


		  }
	},
))

val preferenceCache = mutableMapOf<Pair<Path, String>, Any>()
