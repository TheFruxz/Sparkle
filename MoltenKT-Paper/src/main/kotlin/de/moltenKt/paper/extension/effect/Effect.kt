package de.moltenKt.paper.extension.effect

import de.moltenKt.paper.tool.effect.EntityBasedEffect
import de.moltenKt.paper.tool.effect.LocationBasedEffect
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