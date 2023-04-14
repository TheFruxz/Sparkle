package dev.fruxz.sparkle.framework.system

import dev.fruxz.sparkle.framework.plugin.plugin
import dev.fruxz.sparkle.server.LocalSparklePlugin

val sparkle by lazy {
    plugin<LocalSparklePlugin>()
}