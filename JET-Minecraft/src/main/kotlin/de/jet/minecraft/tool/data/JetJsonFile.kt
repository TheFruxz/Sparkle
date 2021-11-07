package de.jet.minecraft.tool.data

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.extension.data.fromJson
import de.jet.minecraft.extension.data.toJson
import de.jet.minecraft.extension.paper.bukkitVersion
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.tool.data.json.JsonConfiguration
import de.jet.minecraft.tool.data.json.JsonFileDataElement
import de.jet.minecraft.tool.smart.VendorsIdentifiable
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.exists

interface JetJsonFile : JetFile {

	override val file: Path

	override fun load()

	override fun save()

	override operator fun <T : Any?> set(path: String, value: T)

	override operator fun <T> get(path: String): T?

	companion object {

		private fun generateJson(path: Path) =
			object : JetJsonFile {

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

		fun appFile(
			vendor: Identifiable<App>,
			fileName: String,
			extension: String = "json"
		) =
			generateJson(
				Path("JETData", "#${vendor.identity}", "$fileName.$extension")
			)

		fun rootFile(fileName: String, extension: String = "json") =
			generateJson(Path("JETData") / "ROOT" / "$fileName.$extension")

		fun componentFile(component: VendorsIdentifiable<Component>, fileName: String, extension: String = "json"): JetJsonFile =
			generateJson(Path("JETData") / "#${component.identity}@${component.vendorIdentity.identity}" / "$fileName.$extension")

		internal fun dummyComponentFile(dataA: String, dataB: String, fileName: String, extension: String = "json"): JetJsonFile =
			generateJson(Path("JETData") / "#$dataA@$dataB" / "$fileName.$extension")

		fun versionFile(fileName: String, extension: String = "json") =
			generateJson(Path("JETData") / bukkitVersion / "$fileName.$extension")

	}

}