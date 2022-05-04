package de.moltenKt.paper.extension.display

import com.destroystokyo.paper.ParticleBuilder
import de.moltenKt.paper.tool.display.color.MoltenColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Color as BukkitColor
import java.awt.Color as AwtColor

fun colorOf(red: Int, green: Int, blue: Int) = MoltenColor(red, green, blue)

fun colorOf(rgb: Int) = MoltenColor(rgb)

fun colorOf(hexColor: String) = MoltenColor(hexColor)

fun colorOf(awtColor: AwtColor) = MoltenColor(awtColor)

fun colorOf(bukkitColor: BukkitColor) = MoltenColor(bukkitColor)

fun colorOf(textColor: TextColor) = MoltenColor(textColor)

fun ParticleBuilder.color(color: MoltenColor, size: Float) = apply {
    this.color(color.bukkitColor, size)
}

fun ParticleBuilder.color(color: MoltenColor) = apply {
    this.color(color.bukkitColor)
}