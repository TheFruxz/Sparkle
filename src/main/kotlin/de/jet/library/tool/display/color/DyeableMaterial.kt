package de.jet.library.tool.display.color

import org.bukkit.Material

enum class DyeableMaterial(
	val produceMaterialProcess: ColorType.() -> Material,
) {

	WOOL(ColorType::wool),
	TERRACOTTA(ColorType::terracotta),
	CONCRETE(ColorType::concrete),
	CONCRETE_POWDER(ColorType::concretePowder),
	CARPET(ColorType::carpet),
	STAINED_GLASS(ColorType::stainedGlass),
	STAINED_GLASS_PANE(ColorType::stainedGlassPane),
	SHULKER_BOX(ColorType::shulker),
	GLAZED_TERRACOTTA(ColorType::glazedTerracotta),
	BED(ColorType::bed),
	BANNER(ColorType::banner),
	BANNER_WALL(ColorType::bannerWall),
	DYE(ColorType::dyeMaterial);

	fun withColor(color: ColorType) = produceMaterialProcess(color)

	companion object {

		fun fromMaterial(material: Material) = try {
			valueOf(material.name.substringAfter('_'))
		} catch (exception: IllegalArgumentException) {
			null
		}

	}

}
