package de.jet.minecraft.tool.data

import de.jet.library.extension.paper.bukkitVersion
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.tool.smart.VendorsIdentifiable
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.exists

interface JetYamlFile : JetFile {

	companion object {

		private fun generateYaml(path: Path) =
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

		fun appFile(
			vendor: Identifiable<App>,
			fileName: String,
			extension: String = "yml"
		) =
			generateYaml(
				Path("JETData", "#${vendor.identity}", "$fileName.$extension")
			)

		fun rootFile(fileName: String, extension: String = "yml") =
			generateYaml(Path("JETData") / "ROOT" / "$fileName.$extension")

		fun componentFile(component: VendorsIdentifiable<Component>, fileName: String, extension: String = "yml"): JetFile =
			generateYaml(Path("JETData") / "#${component.identity}@${component.vendorIdentity.identity}" / "$fileName.$extension")

		internal fun dummyComponentFile(dataA: String, dataB: String, fileName: String, extension: String = "yml"): JetFile =
			generateYaml(Path("JETData") / "#$dataA@$dataB" / "$fileName.$extension")

		fun versionFile(fileName: String, extension: String = "yml") =
			generateYaml(Path("JETData") / bukkitVersion / "$fileName.$extension")

	}

}