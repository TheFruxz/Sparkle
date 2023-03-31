package dev.fruxz.sparkle.framework.system

import de.fruxz.ascend.extension.tryOrNull
import dev.fruxz.sparkle.framework.sparkle
import java.util.logging.Level

val mainLogger = sparkle.logger

fun mainLog(level: Level = Level.INFO, message: String) = mainLogger.log(level, message)

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = also {
    if (true) { // TODO add debugMode to SparkleApp
        message.lines().forEach { line ->
            mainLog(level, "[DEBUG] $line")
        }
    }
}

fun <T : Any?> T.debugLog(level: Level = Level.WARNING, messageProcess: T.() -> String) {
    if (true) { debugLog(tryOrNull { messageProcess() } ?: ">> Error at getting this debugLog message!", level) } // TODO add debugMode to SparkleApp
}