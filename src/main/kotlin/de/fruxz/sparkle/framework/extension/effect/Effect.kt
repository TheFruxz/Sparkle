package de.fruxz.sparkle.framework.extension.effect

import de.fruxz.sparkle.framework.effect.ComplexEffect
import de.fruxz.sparkle.framework.effect.EffectDsl
import de.fruxz.sparkle.framework.effect.EntityBasedEffect
import de.fruxz.sparkle.framework.effect.LocationBasedEffect
import org.bukkit.Location
import org.bukkit.entity.Entity

/**
 * This function creates a new [ComplexEffect] with the given [builder].
 * @author Fruxz
 * @since 1.0
 */
@EffectDsl
fun buildEffect(builder: ComplexEffect.() -> Unit): ComplexEffect = ComplexEffect(builder)

@EffectDsl
fun Location.playEffect(
	vararg locationEffects: LocationBasedEffect,
): Unit = locationEffects.forEach { it.play(this) }

@EffectDsl
fun Entity.playEffect(
	vararg entityEffects: EntityBasedEffect,
) = entityEffects.forEach { effect ->
	effect.play(this)
}