package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.extension.createFileAndDirectories
import de.fruxz.ascend.extension.data.fromJsonStringOrNull
import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.ascend.extension.data.toJsonElement
import de.fruxz.ascend.extension.data.toJsonElementOrNull
import de.fruxz.ascend.extension.data.toJsonString
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
				((file.readText().toJsonElementOrNull()?.js ?: JsonObject(emptyMap())) + (key to value.toJsonElement())).toJsonString()
			)

			value?.let { it1 -> preferenceCache.put(file to key, it1) } ?: preferenceCache.remove(file to key)

		}
	},
	get = {
		  file.absolute().let {
			  preferenceCache[file to key]?.forceCastOrNull<T>() ?: run {
				  if (it.notExists()) it.createFileAndDirectories()

				  file.readText().toJsonElement().jsonObject[key]?.jsonPrimitive?.content?.fromJsonStringOrNull<T>() ?: run {
					  val value = defaultValue()
					  preferenceCache[file to key] = value
					  value
				  }

			  }


		  }
	},
))

val preferenceCache = mutableMapOf<Pair<Path, String>, Any>()
