package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.extension.createFileAndDirectories
import de.fruxz.ascend.extension.data.readJsonOrDefault
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import kotlin.reflect.KProperty

class SmartConfigurable<T : Any>(
	val file: Path,
	val key: String,
	val default: () -> T,
	val preferenceFormat: PreferenceFormat,
) {

	init {
		tryOrNull { default() }?.let {
			cache = it
			set(it)
		}
	}

	private var cache: T? = null

	fun isValueCached() = cache != null

	fun get(): T = when {
		cache != null -> cache!!
		else -> {
			(preferenceFormat.get(file, key) ?: default()).let {
				cache = it
				it
			}
		}
	}

	fun set(value: T?) {
		cache = value
		preferenceFormat.set(file, key, value)
	}

	operator fun getValue(element: Any, property: KProperty<*>): T = get()

	operator fun setValue(element: Any, property: KProperty<*>, value: T) = set(value)

}

interface PreferenceFormat {

	val extension: String

	fun set(file: Path, key: String, value: Any?)

	fun <T : Any> get(file: Path, key: String): T?

	companion object {

		val json = object : PreferenceFormat {

			override val extension = "json"

			override fun set(file: Path, key: String, value: Any?) {
				file.createFileAndDirectories()
				file.writeJson(file.readJsonOrDefault(mapOf<String, Any>(), true) + (key to value))
			}

			override fun <T : Any> get(file: Path, key: String): T? {
				file.createFileAndDirectories()
				return file.readJsonOrDefault(mapOf<String, Any>(), true)[key].forceCastOrNull()
			}

		}

		val yaml = object : PreferenceFormat {

			override val extension = "yml"

			override fun <T : Any> get(file: Path, key: String): T? {
				file.createFileAndDirectories()
				YamlConfiguration.loadConfiguration(file.toFile()).let {
					it.load(file.toFile())
					return it.get(key)?.forceCastOrNull()
				}
			}

			override fun set(file: Path, key: String, value: Any?) {
				file.createFileAndDirectories()
				YamlConfiguration.loadConfiguration(file.toFile()).let {
					it.set(key, value)
					it.save(file.toFile())
				}
			}

		}

	}

}

fun <T : Any> preference(app: SparkleApp, key: String, format: PreferenceFormat = app.defaultPreferenceFormat, file: Path = SparklePath.appPath(app) / "preferences.${format.extension}", defaultValue: () -> T): SmartConfigurable<T> = SmartConfigurable(file, key, defaultValue, format)

fun <T : Any> preference(component: Component, key: String, format: PreferenceFormat = component.vendor.defaultPreferenceFormat, file: Path = SparklePath.componentPath(component) / "preferences.${format.extension}", defaultValue: () -> T): SmartConfigurable<T> = SmartConfigurable(file, key, defaultValue, format)

fun <T : Any> preference(file: Path, key: String, format: PreferenceFormat = PreferenceFormat.json, defaultValue: () -> T): SmartConfigurable<T> = SmartConfigurable(file, key, defaultValue, format)
