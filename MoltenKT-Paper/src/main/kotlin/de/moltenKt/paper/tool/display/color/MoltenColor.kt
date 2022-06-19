package de.moltenKt.paper.tool.display.color

import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.core.extension.math.limitTo
import de.moltenKt.core.tool.color.Color
import de.moltenKt.core.tool.color.Color.ShiftType
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_SPECTRUM
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_TRANSITION
import de.moltenKt.core.tool.color.ColorBase
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.RGBLike
import kotlin.math.roundToInt
import org.bukkit.Color as BukkitColor
import java.awt.Color as AwtColor

@Serializable
data class MoltenColor(
    @SerialName("r") override val red: Int,
    @SerialName("g") override val green: Int,
    @SerialName("b") override val blue: Int,
) : RGBLike, TextColor, ColorBase<MoltenColor> {

    constructor(textColor: TextColor) : this(textColor.value())

    constructor(bukkitColor: BukkitColor) : this(bukkitColor.asRGB())

    constructor(color: AwtColor) : this(color.red, color.green, color.blue)

    constructor(color: Int) : this(color shr 16 and 0xFF, color shr 8 and 0xFF, color and 0xFF)

    constructor(hexString: String) : this(hexString.removePrefix("#").toInt(16))

    init {
        if (red !in 0..255) error("red must be in range 0..255")
        if (green !in 0..255) error("green must be in range 0..255")
        if (blue !in 0..255) error("blue must be in range 0..255")
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
    override fun shiftTo(color: ColorBase<*>, opacity: Double, shiftType: ShiftType): MoltenColor {
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
    override fun shiftTo(red: Int, green: Int, blue: Int, opacity: Double, shiftType: ShiftType): MoltenColor =
        shiftTo(Color.of(red, green, blue), opacity, shiftType)

    /**
     * This function performs multiple shifts to the [destination] color.
     * The split is done in [parts] amount of parts.
     * The result is a list of colors, which more and more are closer to the [destination] color.
     * @param destination The color, which is the target of the shifts.
     * @param parts The amount of parts, which are used to split the shifts.
     * @author Fruxz
     * @since 1.0
     */
    override fun splitShiftTo(destination: ColorBase<*>, parts: Int): List<MoltenColor> = buildList {
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
    override fun brighter(strength: Double, shiftType: ShiftType): MoltenColor =
        shiftTo(Color.of(AwtColor.WHITE), strength, shiftType)

    /**
     * This function creates a new color, but the [AwtColor.BLACK]
     * is applied to it, by [strength] amount.
     * @author Fruxz
     * @since 1.0
     */
    override fun darker(strength: Double, shiftType: ShiftType): MoltenColor =
        shiftTo(Color.of(AwtColor.BLACK), strength, shiftType)

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

    /**
     * This function creates a new color, but the [color]
     * is applied to it, by [opacity] amount.
     * @author Fruxz
     * @since 1.0
     */
    fun shiftTo(color: MoltenColor, opacity: Double, shiftType: ShiftType = RELATIVE_TO_TRANSITION): MoltenColor {
        validate()
        if (opacity !in 0.0..1.0) error("opacity ($opacity) must be in range 0.0..1.0")

        val raw = Color.of(red, green, blue).shiftTo(color.red, color.green, color.blue, opacity, shiftType)

        return copy(
            red = raw.red,
            green = raw.green,
            blue = raw.blue,
        )
    }

    val bukkitColor: BukkitColor by lazy {
        validate()
        BukkitColor.fromRGB(red, green, blue)
    }

    override fun recreate(red: Int, green: Int, blue: Int) =
        MoltenColor(red, green, blue)

    override fun red(): Int = red

    override fun green(): Int = green

    override fun blue(): Int = blue

    override fun value(): Int = rgb

}