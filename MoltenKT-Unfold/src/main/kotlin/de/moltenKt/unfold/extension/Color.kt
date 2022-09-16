package de.moltenKt.unfold.extension

import de.moltenKt.unfold.color.MoltenColor
import net.kyori.adventure.text.format.TextColor
import java.awt.Color
import de.moltenKt.core.tool.color.Color as CoreColor

fun colorOf(red: Int, green: Int, blue: Int) = MoltenColor(red, green, blue)

fun rgbOf(red: Int, green: Int, blue: Int) = colorOf(red, green, blue)

fun colorOf(rgb: Int) = MoltenColor(rgb)

fun colorOf(hexColor: String) = MoltenColor(hexColor)

fun colorOf(awtColor: Color) = MoltenColor(awtColor)

fun colorOf(textColor: TextColor) = MoltenColor(textColor)

fun CoreColor.asMoltenColor(): MoltenColor = MoltenColor(this.awtColor)