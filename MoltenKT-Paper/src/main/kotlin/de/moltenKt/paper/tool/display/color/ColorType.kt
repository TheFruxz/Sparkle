@file:Suppress("DEPRECATION")

package de.moltenKt.paper.tool.display.color

import de.moltenKt.jvm.annotation.NotPerfect
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.RGBLike
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.boss.BarColor

enum class ColorType : RGBLike {

	WHITE,
	ORANGE,
	MAGENTA,
	LIGHT_BLUE,
	YELLOW,
	LIME,
	PINK,
	GRAY,
	LIGHT_GRAY,
	CYAN,
	PURPLE,
	BLUE,
	BROWN,
	GREEN,
	RED,
	BLACK;

	val dyeColor: DyeColor by lazy {
		when (this) {
			WHITE -> DyeColor.WHITE
			ORANGE -> DyeColor.ORANGE
			MAGENTA -> DyeColor.MAGENTA
			LIGHT_BLUE -> DyeColor.LIGHT_BLUE
			YELLOW -> DyeColor.YELLOW
			LIME -> DyeColor.LIME
			PINK -> DyeColor.PINK
			GRAY -> DyeColor.GRAY
			LIGHT_GRAY -> DyeColor.LIGHT_GRAY
			CYAN -> DyeColor.CYAN
			PURPLE -> DyeColor.PURPLE
			BLUE -> DyeColor.BLUE
			BROWN -> DyeColor.BROWN
			GREEN -> DyeColor.GREEN
			RED -> DyeColor.RED
			BLACK -> DyeColor.BLACK
		}
	}

	@de.moltenKt.jvm.annotation.NotPerfect
	val chatColor by lazy {
		when (this) {
			WHITE -> ChatColor.WHITE
			ORANGE -> ChatColor.GOLD
			MAGENTA -> ChatColor.LIGHT_PURPLE
			LIGHT_BLUE -> ChatColor.BLUE
			YELLOW -> ChatColor.YELLOW
			LIME -> ChatColor.GREEN
			PINK -> ChatColor.DARK_PURPLE
			GRAY -> ChatColor.GRAY
			LIGHT_GRAY -> ChatColor.GRAY
			CYAN -> ChatColor.AQUA
			PURPLE -> ChatColor.DARK_PURPLE
			BLUE -> ChatColor.BLUE
			BROWN -> ChatColor.BLACK
			GREEN -> ChatColor.GREEN
			RED -> ChatColor.RED
			BLACK -> ChatColor.BLACK
		}
	}

	@de.moltenKt.jvm.annotation.NotPerfect
	val dyedChatColor: net.md_5.bungee.api.ChatColor by lazy {
		try {
			net.md_5.bungee.api.ChatColor.of(
				with(java.awt.Color.RGBtoHSB(dyeColor.color.red, dyeColor.color.green, dyeColor.color.blue, null)) {
					java.awt.Color.getHSBColor(get(0), get(1), get(2))
				}
			)
		} catch (ignore: Exception) {
			net.md_5.bungee.api.ChatColor.BLACK
		}
	}

	val rawColor: Color by lazy {
		dyeColor.color
	}

	@de.moltenKt.jvm.annotation.NotPerfect
	val barColor: BarColor by lazy {
		when (this) {
			WHITE -> BarColor.WHITE
			PURPLE -> BarColor.PURPLE
			YELLOW -> BarColor.YELLOW
			GREEN -> BarColor.GREEN
			LIME -> BarColor.GREEN
			RED -> BarColor.RED
			BLUE -> BarColor.BLUE
			LIGHT_BLUE -> BarColor.BLUE
			PINK -> BarColor.PINK
			else -> BarColor.WHITE
		}
	}

	@de.moltenKt.jvm.annotation.NotPerfect
	val adventureBarColor: BossBar.Color by lazy {
		when (this) {
			WHITE -> BossBar.Color.WHITE
			PURPLE -> BossBar.Color.PURPLE
			YELLOW -> BossBar.Color.YELLOW
			GREEN -> BossBar.Color.GREEN
			LIME -> BossBar.Color.GREEN
			RED -> BossBar.Color.RED
			BLUE -> BossBar.Color.BLUE
			LIGHT_BLUE -> BossBar.Color.BLUE
			PINK -> BossBar.Color.PINK
			else -> BossBar.Color.WHITE
		}
	}

	val textColor: TextColor by lazy {
		TextColor.color(rawColor.asRGB())
	}

	val wool: Material by lazy {
		Material.valueOf("${name}_WOOL")
	}

	val terracotta: Material by lazy {
		Material.valueOf("${name}_TERRACOTTA")
	}

	val concrete: Material by lazy {
		Material.valueOf("${name}_CONCRETE")
	}

	val concretePowder: Material by lazy {
		Material.valueOf("${name}_CONCRETE_POWDER")
	}

	val carpet: Material by lazy {
		Material.valueOf("${name}_CARPET")
	}

	val stainedGlass: Material by lazy {
		Material.valueOf("${name}_STAINED_GLASS")
	}

	val stainedGlassPane: Material by lazy {
		Material.valueOf("${name}_STAINED_GLASS_PANE")
	}

	val shulker: Material by lazy {
		Material.valueOf("${name}_SHULKER_BOX")
	}

	val glazedTerracotta: Material by lazy {
		Material.valueOf("${name}_GLAZED_TERRACOTTA")
	}

	val bed: Material by lazy {
		Material.valueOf("${name}_BED")
	}

	val banner: Material by lazy {
		Material.valueOf("${name}_BANNER")
	}

	val bannerWall: Material by lazy {
		Material.valueOf("${name}_WALL_BANNER")
	}

	val dyeMaterial: Material by lazy {
		Material.valueOf("${name}_DYE")
	}

	override fun red() = rawColor.red

	override fun green() = rawColor.green

	override fun blue() = rawColor.blue

	override fun toString() = chatColor.toString()

	companion object {

		@JvmStatic
		fun fromMaterial(material: Material) = try {
			valueOf(material.name.split("_").first())
		} catch (exception: IllegalArgumentException) {
			null
		}

		@JvmStatic
		fun materialFromMaterialCode(materialCode: String) =
			DyeableMaterial.materialFromMaterialCode(materialCode)

	}

}