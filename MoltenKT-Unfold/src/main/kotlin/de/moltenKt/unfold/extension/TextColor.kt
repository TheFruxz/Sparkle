package de.moltenKt.unfold.extension

import de.moltenKt.unfold.Unfold
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.StyleSetter
import net.kyori.adventure.text.format.TextColor
import de.moltenKt.core.tool.color.Color as MoltenCoreColor

@Unfold
infix fun <T : StyleSetter<T>> T.dye(color: TextColor) = color(color)

@Unfold
infix fun <T : StyleSetter<T>> T.dye(color: MoltenCoreColor) = this dye color.asMoltenColor()

fun <T : StyleSetter<T>> T.dyeBlack() = this dye NamedTextColor.BLACK

fun <T : StyleSetter<T>> T.dyeDarkBlue() = this dye NamedTextColor.DARK_BLUE

fun <T : StyleSetter<T>> T.dyeDarkGreen() = this dye NamedTextColor.DARK_GREEN

fun <T : StyleSetter<T>> T.dyeDarkAqua() = this dye NamedTextColor.DARK_AQUA

fun <T : StyleSetter<T>> T.dyeDarkRed() = this dye NamedTextColor.DARK_RED

fun <T : StyleSetter<T>> T.dyeDarkPurple() = this dye NamedTextColor.DARK_PURPLE

fun <T : StyleSetter<T>> T.dyeGold() = this dye NamedTextColor.GOLD

fun <T : StyleSetter<T>> T.dyeGray() = this dye NamedTextColor.GRAY

fun <T : StyleSetter<T>> T.dyeDarkGray() = this dye NamedTextColor.DARK_GRAY

fun <T : StyleSetter<T>> T.dyeBlue() = this dye NamedTextColor.BLUE

fun <T : StyleSetter<T>> T.dyeGreen() = this dye NamedTextColor.GREEN

fun <T : StyleSetter<T>> T.dyeAqua() = this dye NamedTextColor.AQUA

fun <T : StyleSetter<T>> T.dyeRed() = this dye NamedTextColor.RED

fun <T : StyleSetter<T>> T.dyeLightPurple() = this dye NamedTextColor.LIGHT_PURPLE

fun <T : StyleSetter<T>> T.dyeYellow() = this dye NamedTextColor.YELLOW

fun <T : StyleSetter<T>> T.dyeWhite() = this dye NamedTextColor.WHITE