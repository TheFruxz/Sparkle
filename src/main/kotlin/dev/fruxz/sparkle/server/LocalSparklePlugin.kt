package dev.fruxz.sparkle.server

import de.fruxz.ascend.extension.data.kotlinVersion
import org.bukkit.plugin.java.JavaPlugin

class LocalSparklePlugin : JavaPlugin() {

    @Suppress("UnstableApiUsage")
    override fun onEnable() {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

}