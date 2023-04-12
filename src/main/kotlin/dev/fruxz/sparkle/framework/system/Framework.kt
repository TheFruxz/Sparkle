package dev.fruxz.sparkle.framework.system

import dev.fruxz.sparkle.framework.plugin.instance
import dev.fruxz.sparkle.server.LocalSparklePlugin

val sparkle by lazy {
    instance<LocalSparklePlugin>()
}