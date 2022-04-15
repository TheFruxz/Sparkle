package de.moltenKt.paper.tool.data

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.paper.bukkitVersion
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.exists

interface MoltenYamlFile : MoltenFile {

	companion object {

		private fun generateYaml(path: Path) =
			object : MoltenFile {

				override val file =
					path.apply {
						if (!parent.exists())
							parent.createDirectories()
						if (!exists())
							createFile()
					}

				val noPath = file.toFile()
				val yaml = YamlConfiguration.loadConfiguration(noPath)

				override fun load() {
					yaml.load(noPath)
				}

				override fun save() {
					yaml.save(noPath)
				}

				override fun contains(path: String) =
					yaml.contains(path)

				override fun <T : Any?> set(path: String, value: T) {
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

		@JvmStatic
		fun appPath(vendor: Identifiable<App>) = Path("MoltenApps", "#${vendor.identity}")

		@JvmStatic
		fun appFile(
			vendor: Identifiable<App>,
			fileName: String,
			extension: String = "yml"
		) = generateYaml(appPath(vendor) / "$fileName.$extension")

		@JvmStatic
		fun rootPath() =
			Path("MoltenApps") / "MoltenKT"

		@JvmStatic
		fun rootFile(fileName: String, extension: String = "yml") =
			generateYaml(rootPath() / "$fileName.$extension")

		@JvmStatic
		fun componentPath(component: Identifiable<out Component>) =
			Path("MoltenApps") / "#${
				with(component.identity.split(":")) {
					"${this[1]}@${this[0]}"
				}}"

		@JvmStatic
		fun componentFile(component: Identifiable<out Component>, fileName: String, extension: String = "yml"): MoltenFile =
			generateYaml(componentPath(component) / "$fileName.$extension")

		@JvmStatic
		fun versionPath(version: String = bukkitVersion) =
			Path("MoltenApps") / version

		@JvmStatic
		fun versionFile(fileName: String, extension: String = "yml") =
			generateYaml(versionPath() / "$fileName.$extension")

		internal fun dummyComponentFile(dataA: String, dataB: String, fileName: String, extension: String = "yml"): MoltenFile =
			generateYaml(Path("MoltenApps") / "#$dataA@$dataB" / "$fileName.$extension")

	}

}