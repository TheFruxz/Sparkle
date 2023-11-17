package dev.fruxz.sparkle.framework.system

import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.sparkle.framework.plugin.plugin
import dev.fruxz.sparkle.framework.ux.messaging.Transmission
import dev.fruxz.sparkle.server.LocalSparklePlugin
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.format.TextColor

val sparkle by lazy {
    tryOrNull { plugin<LocalSparklePlugin>() } ?: throw IllegalStateException("Sparkle is not loaded, maybe access outside/before onLoad?")
}

internal val sparklePrefix =
    (text("\uD83D\uDD25 ").style(TextColor.color(255, 0, 64)) + text("» ").style(TextColor.color(74, 74, 74)))

internal fun Transmission.sparklePrefix() = prefix(sparklePrefix)