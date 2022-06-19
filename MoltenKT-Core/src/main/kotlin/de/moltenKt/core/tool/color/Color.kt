package de.moltenKt.core.tool.color

import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_SPECTRUM
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_TRANSITION
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
open class Color private constructor(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
) : ColorBase<Color> {

    override val identity: String by lazy {
        hexString
    }

    /**
     * This function creates a new color, but the [color]
     * is applied to it, by [opacity] amount.
     * @author Fruxz
     * @since 1.0
     */
    override fun shiftTo(color: ColorBase<*>, opacity: Double, shiftType: ShiftType): Color {
        validate()
        if (opacity !in 0.0..1.0) error("opacity ($opacity) must be in range 0.0..1.0")

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

        return recreate(red = (red + redModifier), green = (green + greenModifier), blue = (blue + blueModifier))
    }

    /**
     * This function creates a new color, but the [red], [green] and [blue]
     * is applied to it, by [opacity] amount.
     * @author Fruxz
     * @since 1.0
     */
    override fun shiftTo(red: Int, green: Int, blue: Int, opacity: Double, shiftType: ShiftType): Color =
        shiftTo(of(red, green, blue), opacity, shiftType)

    /**
     * This function performs multiple shifts to the [destination] color.
     * The split is done in [parts] amount of parts.
     * The result is a list of colors, which more and more are closer to the [destination] color.
     * @param destination The color, which is the target of the shifts.
     * @param parts The amount of parts, which are used to split the shifts.
     * @author Fruxz
     * @since 1.0
     */
    override fun splitShiftTo(destination: ColorBase<*>, parts: Int): List<Color> = buildList {
        val step = 1.0 / parts
        var opacity = 0.0
        repeat(parts + 1) {
            add(shiftTo(destination, opacity.limitTo(.0..1.0), RELATIVE_TO_TRANSITION))
            opacity += step
        }
    }

    /**
     * This function creates a new color, but the [AwtColor.WHITE]
     * is applied to it, by [strength] amount.
     * @author Fruxz
     * @since 1.0
     */
    override fun brighter(strength: Double, shiftType: ShiftType): Color =
        shiftTo(of(AwtColor.WHITE), strength, shiftType)

    /**
     * This function creates a new color, but the [AwtColor.BLACK]
     * is applied to it, by [strength] amount.
     * @author Fruxz
     * @since 1.0
     */
    override fun darker(strength: Double, shiftType: ShiftType): Color =
        shiftTo(of(AwtColor.BLACK), strength, shiftType)

    override fun recreate(red: Int, green: Int, blue: Int): Color =
        Color(red, green, blue)

    override val rgb: Int by lazy {
        validate()
        (red shl 16) or (green shl 8) or blue
    }

    override val awtColor: AwtColor by lazy {
        validate()
        AwtColor(red, green, blue)
    }

    override val hexString: String by lazy {
        validate()
        String.format("#%02x%02x%02x", red, green, blue)
    }

    override val rgbString: String by lazy {
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