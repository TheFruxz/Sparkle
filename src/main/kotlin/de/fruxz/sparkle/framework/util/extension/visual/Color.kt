package de.fruxz.sparkle.framework.util.extension.visual

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.ascend.tool.color.Color
import de.fruxz.stacked.color.KotlinColor
import de.fruxz.stacked.extension.asKotlinColor
import org.bukkit.Color as BukkitColor

fun ParticleBuilder.color(color: KotlinColor, size: Float) = apply {
    this.color(color.bukkitColor, size)
}

fun ParticleBuilder.color(color: KotlinColor) = apply {
    this.color(color.bukkitColor)
}

fun Color.asBukkitColor(): org.bukkit.Color = asKotlinColor().bukkitColor

fun colorOf(bukkitColor: BukkitColor) = KotlinColor.Companion.ofBukkit(bukkitColor)

fun KotlinColor.Companion.ofBukkit(bukkitColor: BukkitColor) = KotlinColor(bukkitColor.asRGB())

val KotlinColor.bukkitColor: BukkitColor
    get() {
        validate()
        return BukkitColor.fromRGB(red, green, blue)
    }