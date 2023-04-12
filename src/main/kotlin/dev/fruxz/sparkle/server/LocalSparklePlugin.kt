package dev.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.sparkle.framework.SparklePlugin

class LocalSparklePlugin : SparklePlugin({

    onLoad {
        logger.info("Loaded ${LocalSparkleLoader.dependencies.size} dependencies!")
    }

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

}) {

    companion object {
        const val SYSTEM_IDENTITY = "Sparkle"
    }

}