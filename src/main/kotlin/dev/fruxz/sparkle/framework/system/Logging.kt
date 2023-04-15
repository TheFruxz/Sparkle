package dev.fruxz.sparkle.framework.system

import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.sparkle.server.LocalSparklePlugin
import java.util.logging.Level
import java.util.logging.Logger

val mainLogger: Logger = sparkle.logger

fun mainLog(level: Level = Level.INFO, message: String) = mainLogger.log(level, message)

fun <T : Any?> T.debugLog(message: String, level: Level = Level.WARNING) = also {
    if (LocalSparklePlugin.debugMode) {
        message.lines().forEach { line ->
            mainLog(level, "[DEBUG] $line")
        }
    }
}

fun <T : Any?> T.debugLog(level: Level = Level.WARNING, messageProcess: T.() -> String) {
    if (LocalSparklePlugin.debugMode) { debugLog(tryOrNull { messageProcess() } ?: ">> Error at getting this debugLog message!", level) }
}