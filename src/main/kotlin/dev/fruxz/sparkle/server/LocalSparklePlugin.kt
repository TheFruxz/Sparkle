package dev.fruxz.sparkle.server

import de.fruxz.ascend.extension.createFileAndDirectories
import de.fruxz.ascend.extension.data.kotlinVersion
import de.fruxz.ascend.json.property
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.server.command.SparkleCommand
import org.bukkit.Bukkit
import java.nio.file.Path
import kotlin.io.path.div

class LocalSparklePlugin : SparklePlugin({

    onLoad {
        logger.info("Loaded ${LocalSparkleLoader.dependencies.size} dependencies!")
    }

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

    command<SparkleCommand>()

}) {

    companion object {

        const val SYSTEM_IDENTITY = "Sparkle"

        private val configFile = (Bukkit.getPluginsFolder().toPath() / "Sparkle" / "config.json").also(Path::createFileAndDirectories)

        var debugMode by property(configFile, "debugMode") { false }
            internal set

    }

}