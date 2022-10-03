package de.fruxz.sparkle.framework.util.data.yaml

import de.fruxz.sparkle.framework.util.data.SparkleFile
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists

interface SparkleYamlFile : SparkleFile {

    companion object {

        @JvmStatic
        fun generateYaml(path: Path) =
            object : SparkleFile {

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

    }

}