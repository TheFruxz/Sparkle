package de.moltenKt.paper.tool.data

import de.moltenKt.jvm.extension.data.fromJson
import de.moltenKt.jvm.extension.data.toJson
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.paper.bukkitVersion
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.data.json.JsonConfiguration
import de.moltenKt.paper.tool.data.json.JsonFileDataElement
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.exists

interface MoltenJsonFile : MoltenFile {

	override val file: Path

	override fun load()

	override fun save()

	override operator fun <T : Any?> set(path: String, value: T)

	override operator fun <T> get(path: String): T?

	companion object {

		@JvmStatic
		fun generateJson(path: Path) =
			object : MoltenJsonFile {

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
						content.fromJson()
					} else {
						noPath.writeText(JsonConfiguration(elements = emptyList()).toJson())
						JsonConfiguration(elements = emptyList())
					}

				}

				override fun save() {
					noPath.writeText(state.toJson())
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