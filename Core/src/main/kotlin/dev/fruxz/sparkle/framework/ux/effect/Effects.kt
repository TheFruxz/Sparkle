package dev.fruxz.sparkle.framework.ux.effect

import dev.fruxz.sparkle.framework.ux.effect.combinations.ComplexEffect
import dev.fruxz.sparkle.framework.ux.effect.specifications.EntityBasedEffect
import dev.fruxz.sparkle.framework.ux.effect.specifications.LocationBasedEffect
import org.bukkit.Location
import org.bukkit.entity.Entity

@EffectDsl
fun buildEffect(builder: ComplexEffect.() -> Unit): ComplexEffect = ComplexEffect(builder)

@EffectDsl
fun Location.playEffect(
    vararg locationEffects: LocationBasedEffect,
) = locationEffects.forEach { it.play(this) }

@EffectDsl
fun Entity.playEffect(
    vararg entityEffects: EntityBasedEffect,
) = entityEffects.forEach { effect -> effect.play(this) }