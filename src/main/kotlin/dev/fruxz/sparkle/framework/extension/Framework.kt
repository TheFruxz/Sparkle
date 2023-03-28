package dev.fruxz.sparkle.framework.extension

import dev.fruxz.sparkle.server.LocalSparklePlugin

val sparkle by lazy {
    instance<LocalSparklePlugin>()
}