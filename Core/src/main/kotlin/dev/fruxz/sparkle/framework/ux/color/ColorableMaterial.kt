package dev.fruxz.sparkle.framework.ux.color

import dev.fruxz.ascend.extension.tryOrNull
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Material

enum class ColorableMaterial(
    val materialProcess: ColorType.() -> Material,
) : Keyed {

    WOOL(ColorType::wool),
    CARPET(ColorType::carpet),
    TERRACOTTA(ColorType::terracotta),
    CONCRETE(ColorType::concrete),
    CONCRETE_POWDER(ColorType::concretePowder),
    GLAZED_TERRACOTTA(ColorType::glazedTerracotta),
    STAINED_GLASS(ColorType::stainedGlass),
    STAINED_GLASS_PANE(ColorType::stainedGlassPane),
    SHULKER_BOX(ColorType::shulkerBox),
    BED(ColorType::bed),
    CANDLE(ColorType::candle),
    CANDLE_CAKE(ColorType::candleCake),
    BANNER(ColorType::banner),
    BANNER_WALL(ColorType::bannerWall),
    DYE(ColorType::dye);

    fun withColor(color: ColorType): Material = color.materialProcess()

    operator fun plus(color: ColorType): Material = withColor(color)

    infix fun dyed(color: ColorType): Material = withColor(color)

    override fun key(): Key = Key.key(name.lowercase())

    companion object {

        fun fromMaterial(material: Material) = tryOrNull {
            entries.firstOrNull { material.name.endsWith(it.name) }
        }

    }

}