package de.moltenKt.paper.tool.display.color

import de.moltenKt.core.tool.color.Color
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
) : RGBLike, TextColor, Color<MoltenColor>(red, green, blue) {

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
    fun shiftTo(color: MoltenColor, opacity: Double): MoltenColor {
        validate()
        if (opacity !in 0.0..1.0) error("opacity must be in range 0.0..1.0")

        println("o -> $opacity and ${opacity in 0.0..1.0}")

        return copy(
            red = (red + (color.red - red) * opacity).roundToInt(),
            green = (green + (color.green - green) * opacity).roundToInt(),
            blue = (blue + (color.blue - blue) * opacity).roundToInt(),
        )
    }

    val bukkitColor: BukkitColor by lazy {
        validate()
        BukkitColor.fromRGB(red, green, blue)
    }

    override fun red(): Int = red

    override fun green(): Int = green

    override fun blue(): Int = blue

    override fun value(): Int = rgb

}