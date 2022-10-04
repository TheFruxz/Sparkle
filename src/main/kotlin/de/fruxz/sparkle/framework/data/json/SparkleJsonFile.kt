package de.fruxz.sparkle.framework.data.json

import de.fruxz.ascend.extension.data.fromJsonString
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.sparkle.framework.data.SparkleFile
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists

interface SparkleJsonFile : SparkleFile {

	override val file: Path

	override fun load()

	override fun save()

	override operator fun <T : Any?> set(path: String, value: T)

	override operator fun <T> get(path: String): T?

	companion object {

		@JvmStatic
		fun generateJson(path: Path) =
			object : SparkleJsonFile {

				override val file =
					path.apply {
						if (!parent.exists())
							parent.createDirectories()
						if (!exists())
							createFile()
					}

				var state = JsonConfiguration(elements = emptyList())
					private set

				val noPath = file.toFile()

				override fun load() {
					val content = noPath.readText()

					state = if (content.isNotBlank()) {
						content.fromJsonString()
					} else {
						noPath.writeJson(JsonConfiguration(elements = emptyList()))
						JsonConfiguration(elements = emptyList())
					}

				}

				override fun save() {
					noPath.writeJson(state)
				}

				override fun contains(path: String) =
					state.elements.any { it.path == path }

				override operator fun <T : Any?> set(path: String, value: T) {
					val elements = state.elements.toMutableList()

					elements.removeAll { it.path == path }

					if (value != null)
						elements.add(JsonFileDataElement(path, value))

					state = state.copy(elements = elements)
				}

				@Suppress("UNCHECKED_CAST")
				override fun <T> get(path: String): T? {
					return state.elements.firstOrNull { it.path == path }?.value as T?
				}

			}

	}

}