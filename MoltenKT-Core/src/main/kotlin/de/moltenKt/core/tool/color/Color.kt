package de.moltenKt.core.tool.color

import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_SPECTRUM
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_TRANSITION
import de.moltenKt.core.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
import java.awt.Color as AwtColor

/**
 * This class helps to provide an easy and fast-to-use color
 * api for Kotlin! This is only a gateway to other color classes,
 * but this helps to modify and convert colors easily.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
open class Color<T : Color<T>> constructor(
    open val red: Int,
    open val green: Int,
    open val blue: Int,
) : Identifiable<Color<T>> {

    fun validate() {
        if (red !in 0..255) error("red must be in range of 0..255")
        if (green !in 0..255) error("green must be in range of 0..255")
        if (blue !in 0..255) error("blue must be in range of 0..255")
    }

    override val identity: String by lazy {
        hexString
    }

    /**
     * This function creates a new color, but the [color]
     * is applied to it, by [opacity] amount.
     * @author Fruxz
     * @since 1.0
     */
    fun shiftTo(color: Color<*>, opacity: Double, shiftType: ShiftType = RELATIVE_TO_SPECTRUM): T {
        validate()
        if (opacity !in 0.0..1.0) error("opacity must be in range 0.0..1.0")

        val spectrumLimiter = 255.0 * opacity

        val redModifier = when (shiftType) {
            RELATIVE_TO_SPECTRUM -> (color.red - red).limitTo(-spectrumLimiter.floorToInt()..spectrumLimiter.ceilToInt())
            else -> ((color.red - red) * opacity).roundToInt()
        }
        val greenModifier = when (shiftType) {
            RELATIVE_TO_SPECTRUM -> (color.green - green).limitTo(-spectrumLimiter.floorToInt()..spectrumLimiter.ceilToInt())
            else -> ((color.green - green) * opacity).roundToInt()
        }
        val blueModifier = when (shiftType) {
            RELATIVE_TO_SPECTRUM -> (color.blue - blue).limitTo(-spectrumLimiter.floorToInt()..spectrumLimiter.ceilToInt())
            else -> ((color.blue - blue) * opacity).roundToInt()
        }

        return Color(
            red = (red + redModifier),
            green = (green + greenModifier),
            blue = (blue + blueModifier),
        ).forceCast()
    }

    /**
     * This function creates a new color, but the [red], [green] and [blue]
     * is applied to it, by [opacity] amount.
     * @author Fruxz
     * @since 1.0
     */
    fun shiftTo(red: Int, green: Int, blue: Int, opacity: Double, shiftType: ShiftType = RELATIVE_TO_SPECTRUM) =
        shiftTo(of(red, green, blue), opacity, shiftType)

    /**
     * This function creates a new color, but the [AwtColor.WHITE]
     * is applied to it, by [strength] amount.
     * @author Fruxz
     * @since 1.0
     */
    fun brighter(strength: Double = .2, shiftType: ShiftType = RELATIVE_TO_TRANSITION) =
        shiftTo(of(AwtColor.WHITE), strength, shiftType)

    /**
     * This function creates a new color, but the [AwtColor.BLACK]
     * is applied to it, by [strength] amount.
     * @author Fruxz
     * @since 1.0
     */
    fun darker(strength: Double = .2, shiftType: ShiftType = RELATIVE_TO_TRANSITION) =
        shiftTo(of(AwtColor.BLACK), strength, shiftType)

    val rgb: Int by lazy {
        validate()
        (red shl 16) or (green shl 8) or blue
    }

    val awtColor: AwtColor by lazy {
        validate()
        AwtColor(red, green, blue)
    }

    val hexString: String by lazy {
        validate()
        String.format("#%02x%02x%02x", red, green, blue)
    }

    val rgbString: String by lazy {
        validate()
        String.format("rgb(%d, %d, %d)", red, green, blue)
    }

    companion object {

        fun of(red: Int, green: Int, blue: Int) = Color(red, green, blue)

        fun of(color: AwtColor) = Color(color.red, color.green, color.blue)

        fun of(rgb: Int) = Color(rgb shr 16 and 0xFF, rgb shr 8 and 0xFF, rgb and 0xFF)

        fun of(hexString: String) = of(hexString.removePrefix("#").toInt(16))

    }

    enum class ShiftType {
        /**
         * Opacity relative to the transition: 0.05 Opacity with (0 shiftTo 20) = 1
         */
        RELATIVE_TO_TRANSITION,

        /**
         * Opacity relative to the whole spectrum: 0.05 Opacity with (0 shiftTo 20) = 12,75
         */
        RELATIVE_TO_SPECTRUM,
    }

}