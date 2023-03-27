package dev.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.TestCommand

class LocalSparklePlugin : SparklePlugin({

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

    command<TestCommand>()

})