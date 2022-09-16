package de.moltenKt.paper.extension.display

import com.destroystokyo.paper.ParticleBuilder
import de.moltenKt.core.tool.color.Color
import de.moltenKt.unfold.color.MoltenColor
import de.moltenKt.unfold.extension.asMoltenColor
import org.bukkit.Color as BukkitColor

fun ParticleBuilder.color(color: MoltenColor, size: Float) = apply {
    this.color(color.bukkitColor, size)
}

fun ParticleBuilder.color(color: MoltenColor) = apply {
    this.color(color.bukkitColor)
}

fun Color.asBukkitColor(): org.bukkit.Color = asMoltenColor().bukkitColor

fun colorOf(bukkitColor: BukkitColor) = MoltenColor.Companion.ofBukkit(bukkitColor)

fun MoltenColor.Companion.ofBukkit(bukkitColor: BukkitColor) = MoltenColor(bukkitColor.asRGB())

val MoltenColor.bukkitColor: BukkitColor
    get() {
        validate()
        return BukkitColor.fromRGB(red, green, blue)
    }