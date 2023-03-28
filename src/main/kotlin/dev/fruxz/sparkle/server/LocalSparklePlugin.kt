package dev.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.TestCommand
import dev.fruxz.sparkle.framework.extension.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class LocalSparklePlugin : SparklePlugin({

    onLoad {
        logger.info("Loaded ${LocalSparkleLoader.dependencies.size} dependencies!")
    }

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
        coroutineScope().launch {
            delay(5.seconds)
            println("Waiting 5 seconds...")
            delay(5.seconds)
            println("Got it!")
        }
    }

    command<TestCommand>()

}) {

    val test = "This is a transported variable!"

    companion object {

        const val SYSTEM_IDENTITY = "Sparkle"

    }

}