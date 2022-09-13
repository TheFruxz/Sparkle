package de.moltenKt.unfold.extension

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.StyleSetter

fun <T : StyleSetter<T>> T.dyeBlack() = color(NamedTextColor.BLACK)

fun <T : StyleSetter<T>> T.dyeDarkBlue() = color(NamedTextColor.DARK_BLUE)

fun <T : StyleSetter<T>> T.dyeDarkGreen() = color(NamedTextColor.DARK_GREEN)

fun <T : StyleSetter<T>> T.dyeDarkAqua() = color(NamedTextColor.DARK_AQUA)

fun <T : StyleSetter<T>> T.dyeDarkRed() = color(NamedTextColor.DARK_RED)

fun <T : StyleSetter<T>> T.dyeDarkPurple() = color(NamedTextColor.DARK_PURPLE)

fun <T : StyleSetter<T>> T.dyeGold() = color(NamedTextColor.GOLD)

fun <T : StyleSetter<T>> T.dyeGray() = color(NamedTextColor.GRAY)

fun <T : StyleSetter<T>> T.dyeDarkGray() = color(NamedTextColor.DARK_GRAY)

fun <T : StyleSetter<T>> T.dyeBlue() = color(NamedTextColor.BLUE)

fun <T : StyleSetter<T>> T.dyeGreen() = color(NamedTextColor.GREEN)

fun <T : StyleSetter<T>> T.dyeAqua() = color(NamedTextColor.AQUA)

fun <T : StyleSetter<T>> T.dyeRed() = color(NamedTextColor.RED)

fun <T : StyleSetter<T>> T.dyeLightPurple() = color(NamedTextColor.LIGHT_PURPLE)

fun <T : StyleSetter<T>> T.dyeYellow() = color(NamedTextColor.YELLOW)

fun <T : StyleSetter<T>> T.dyeWhite() = color(NamedTextColor.WHITE)