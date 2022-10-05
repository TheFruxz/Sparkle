package de.fruxz.sparkle.framework.extension.effect

import de.fruxz.sparkle.framework.effect.EntityBasedEffect
import de.fruxz.sparkle.framework.effect.LocationBasedEffect
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