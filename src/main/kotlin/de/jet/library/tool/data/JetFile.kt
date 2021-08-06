package de.jet.library.tool.data

import de.jet.library.structure.app.App
import de.jet.library.structure.component.Component
import de.jet.library.structure.smart.Identifiable
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.exists

interface JetFile {

	val file: Path

	val loader: YamlConfiguration

	fun load()

	fun save()

	fun set(path: String, value: Any?)

	fun <T> get(path: String): T?

	companion object {

		private fun generate(path: Path) =
			object : JetFile {

				override val file =
					path.apply {
						if (!parent.exists())
							parent.createDirectories()
						if (!exists())
							createFile()
					}

				val noPath = file.toFile()
				val yaml = YamlConfiguration.loadConfiguration(noPath)
				override val loader: YamlConfiguration
					get() = yaml

				override fun load() {
					yaml.load(noPath)
				}

				override fun save() {
					yaml.save(noPath)
				}

				override fun set(path: String, value: Any?) {
					yaml.set(path, value)
				}

				@Suppress("UNCHECKED_CAST")
				override fun <T> get(path: String): T? {
					val get = yaml.get(path)

					return try {
						get as T?
					} catch (e: ClassCastException) {
						null
					}

				}

			}

		fun appFile(
			vendor: Identifiable<App>,
			fileName: String,
			extension: String = "yml"
		) =
			generate(
				Path("JETData", "#${vendor.id}", "$fileName.$extension")
			)

		fun rootFile(fileName: String, extension: String = "yml") =
			generate(Path("JETData") / "ROOT" / "$fileName.$extension")

		fun componentFile(component: Component, fileName: String, extension: String = "yml"): JetFile =
			generate(Path("JETData") / "#${component.id}@${component.vendor.id}" / "$fileName.$extension")

		fun versionFile(fileName: String, extension: String = "yml") =
			generate(Path("JETData") / Bukkit.getBukkitVersion() / "$fileName.$extension")

	}

}