package de.fruxz.sparkle.extension.effect

import de.fruxz.sparkle.tool.effect.EntityBasedEffect
import de.fruxz.sparkle.tool.effect.LocationBasedEffect
import org.bukkit.Location
import org.bukkit.entity.Entity

fun Location.playEffect(
	vararg locationEffects: LocationBasedEffect,
): Unit = locationEffects.forEach { it.play(this) }

fun Entity.playEffect(
	vararg entityEffects: EntityBasedEffect,
) = entityEffects.forEach { effect ->
	effect.play(this)
}