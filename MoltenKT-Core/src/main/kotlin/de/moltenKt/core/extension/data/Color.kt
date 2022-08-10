package de.moltenKt.core.extension.data

import de.moltenKt.core.tool.color.Color
import java.awt.Color as AwtColor

fun rgb(red: Int, green: Int, blue: Int) = Color.of(red, green, blue)

fun rgbAwt(red: Int, green: Int, blue: Int) = AwtColor(red, green, blue)

fun hsb(h: Float, s: Float, b: Float) = Color.of(hsbAwt(h, s, b))

fun hsbAwt(h: Float, s: Float, b: Float): AwtColor = AwtColor.getHSBColor(h, s, b)