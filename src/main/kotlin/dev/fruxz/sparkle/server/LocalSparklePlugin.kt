package dev.fruxz.sparkle.server

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.ascend.json.property
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.system.pluginsFolder
import dev.fruxz.sparkle.server.command.SparkleCommand
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

        private val configFile = (pluginsFolder / "Sparkle" / "config.json").also(Path::createFileAndDirectories)

        var debugMode by property(configFile, "debugMode") { false }
            internal set

    }

}