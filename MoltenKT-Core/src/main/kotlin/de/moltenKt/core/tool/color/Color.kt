package de.moltenKt.core.tool.color

import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
import java.awt.Color as AwtColor

@Serializable
open class Color<T : Color<T>> constructor(
    open val red: Int,
    open val green: Int,
    open val blue: Int,
) : Identifiable<Color<T>> {

    init {
        if (red !in 0..255) error("red must be in range 0..255")
        if (green !in 0..255) error("green must be in range 0..255")
        if (blue !in 0..255) error("blue must be in range 0..255")
    }

    override val identity: String by lazy {
        hexString
    }

    fun shiftTo(color: Color<*>, opacity: Double): T {
        if (opacity !in 0.0..1.0) error("opacity must be in range 0.0..1.0")

        println("o -> $opacity and ${opacity in 0.0..1.0}")

        return Color(
            red = (red + (color.red - red) * opacity).roundToInt(),
            green = (green + (color.green - green) * opacity).roundToInt(),
            blue = (blue + (color.blue - blue) * opacity).roundToInt(),
        ).forceCast()
    }

    fun shiftTo(red: Int, green: Int, blue: Int, opacity: Double) =
        shiftTo(of(red, green, blue), opacity)

    fun brighter(strength: Double) =
        shiftTo(of(AwtColor.WHITE), strength)

    fun darker(strength: Double) =
        shiftTo(of(AwtColor.BLACK), strength)

    val rgb: Int by lazy {
        (red shl 16) or (green shl 8) or blue
    }

    val awtColor: AwtColor by lazy {
        AwtColor(red, green, blue)
    }

    val hexString: String by lazy {
        String.format("#%02x%02x%02x", red, green, blue)
    }

    val rgbString: String by lazy {
        String.format("rgb(%d, %d, %d)", red, green, blue)
    }

    companion object {

        fun of(red: Int, green: Int, blue: Int) = Color(red, green, blue)

        fun of(color: AwtColor) = Color(color.red, color.green, color.blue)

        fun of(rgb: Int) = Color(rgb shr 16 and 0xFF, rgb shr 8 and 0xFF, rgb and 0xFF)

        fun of(hexString: String) = of(hexString.removePrefix("#").toInt(16))

    }

}