package de.fruxz.sparkle.framework.effect

import org.bukkit.Location
import org.bukkit.entity.Entity

data class ComplexEffect(
	var effects: List<CrossBasedEffect>
) : CrossBasedEffect {

	@EffectDsl
	constructor(builder: ComplexEffect.() -> Unit) : this(ComplexEffect(emptyList()).apply(builder).effects)

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
		effects.forEach { effect -> effect.play(locations, entities) }

	override fun play(vararg entities: Entity?): Unit =
		effects.forEach { effect -> effect.play(entities = entities) }

	override fun play(vararg locations: Location?): Unit =
		effects.forEach { effect -> effect.play(locations = locations) }

	/**
	 * This function adds the [effect] to the [effects]
	 * list of this [ComplexEffect].
	 * @author Fruxz
	 * @since 1.0
	 */
	@EffectDsl
	fun effect(effect: CrossBasedEffect) {
		effects += effect
	}



}
