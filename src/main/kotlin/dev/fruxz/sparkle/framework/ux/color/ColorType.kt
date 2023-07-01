package dev.fruxz.sparkle.framework.ux.color

import dev.fruxz.ascend.extension.tryOrNull
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.RGBLike
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material

enum class ColorType : RGBLike, Keyed {

    WHITE,
    LIGHT_GRAY,
    GRAY,
    BLACK,
    BROWN,
    RED,
    ORANGE,
    YELLOW,
    LIME,
    GREEN,
    CYAN,
    LIGHT_BLUE,
    BLUE,
    PURPLE,
    MAGENTA,
    PINK;

    override fun key(): Key = Key.key(name.lowercase())

    val dyeColor: DyeColor by lazy {
        when (this) {
            WHITE -> DyeColor.WHITE
            LIGHT_GRAY -> DyeColor.LIGHT_GRAY
            GRAY -> DyeColor.GRAY
            BLACK -> DyeColor.BLACK
            BROWN -> DyeColor.BROWN
            RED -> DyeColor.RED
            ORANGE -> DyeColor.ORANGE
            YELLOW -> DyeColor.YELLOW
            LIME -> DyeColor.LIME
            GREEN -> DyeColor.GREEN
            CYAN -> DyeColor.CYAN
            LIGHT_BLUE -> DyeColor.LIGHT_BLUE
            BLUE -> DyeColor.BLUE
            PURPLE -> DyeColor.PURPLE
            MAGENTA -> DyeColor.MAGENTA
            PINK -> DyeColor.PINK
        }
    }

    val textColor: TextColor by lazy {
        when (this) {
            WHITE -> NamedTextColor.WHITE
            LIGHT_GRAY -> NamedTextColor.GRAY
            GRAY -> NamedTextColor.DARK_GRAY
            BLACK -> NamedTextColor.BLACK
            BROWN -> TextColor.color(DyeColor.BROWN.color.asRGB())
            RED -> NamedTextColor.RED
            ORANGE -> TextColor.color(DyeColor.ORANGE.color.asRGB())
            YELLOW -> NamedTextColor.YELLOW
            LIME -> NamedTextColor.GREEN
            GREEN -> NamedTextColor.DARK_GREEN
            CYAN -> TextColor.color(DyeColor.CYAN.color.asRGB())
            LIGHT_BLUE -> NamedTextColor.BLUE
            BLUE -> NamedTextColor.DARK_BLUE
            PURPLE -> NamedTextColor.DARK_PURPLE
            MAGENTA -> NamedTextColor.LIGHT_PURPLE
            PINK -> TextColor.color(DyeColor.PINK.color.asRGB())
        }
    }

    val rawColor: Color by lazy { dyeColor.color }

    val barColor: BossBar.Color by lazy {
        when (this) {
            WHITE -> BossBar.Color.WHITE
            LIGHT_GRAY -> BossBar.Color.WHITE
            GRAY -> BossBar.Color.WHITE
            BLACK -> BossBar.Color.WHITE
            BROWN -> BossBar.Color.WHITE
            RED -> BossBar.Color.RED
            ORANGE -> BossBar.Color.YELLOW
            YELLOW -> BossBar.Color.YELLOW
            LIME -> BossBar.Color.GREEN
            GREEN -> BossBar.Color.GREEN
            CYAN -> BossBar.Color.BLUE
            LIGHT_BLUE -> BossBar.Color.BLUE
            BLUE -> BossBar.Color.BLUE
            PURPLE -> BossBar.Color.PINK
            MAGENTA -> BossBar.Color.PINK
            PINK -> BossBar.Color.PINK
        }
    }

    // block properties

    val wool: Material by lazy { Material.valueOf("${name}_WOOL") }

    val carpet: Material by lazy { Material.valueOf("${name}_CARPET") }

    val terracotta: Material by lazy { Material.valueOf("${name}_TERRACOTTA") }

    val concrete: Material by lazy { Material.valueOf("${name}_CONCRETE") }

    val concretePowder: Material by lazy { Material.valueOf("${name}_CONCRETE_POWDER") }

    val glazedTerracotta: Material by lazy { Material.valueOf("${name}_GLAZED_TERRACOTTA") }

    val stainedGlass: Material by lazy { Material.valueOf("${name}_STAINED_GLASS") }

    val stainedGlassPane: Material by lazy { Material.valueOf("${name}_STAINED_GLASS_PANE") }

    val shulkerBox: Material by lazy { Material.valueOf("${name}_SHULKER_BOX") }

    val bed: Material by lazy { Material.valueOf("${name}_BED") }

    val candle: Material by lazy { Material.valueOf("${name}_CANDLE") }

    val candleCake: Material by lazy { Material.valueOf("${name}_CANDLE_CAKE") }

    val banner: Material by lazy { Material.valueOf("${name}_BANNER") }

    val bannerWall: Material by lazy { Material.valueOf("${name}_WALL_BANNER") }

    val dye: Material by lazy { Material.valueOf("${name}_DYE") }

    // overrides

    override fun red(): Int = rawColor.red

    override fun green(): Int = rawColor.green

    override fun blue(): Int = rawColor.blue

    companion object {

        fun fromMaterial(material: Material): ColorType? = tryOrNull {
            entries.firstOrNull { color -> material.name.startsWith(color.name) }
        }

        val rainbow: List<ColorType> by lazy { listOf(RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT_BLUE, BLUE, PURPLE, MAGENTA, PINK) }

    }

}