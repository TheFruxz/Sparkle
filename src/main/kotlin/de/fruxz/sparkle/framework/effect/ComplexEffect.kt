package de.fruxz.sparkle.framework.effect

import org.bukkit.Location
import org.bukkit.entity.Entity

data class ComplexEffect(
	val effects: Set<CrossBasedEffect>
) : Effect, CrossBasedEffect {

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
		effects.forEach { effect -> effect.play(locations, entities) }

	override fun play(vararg entities: Entity?): Unit =
		effects.forEach { effect -> effect.play(entities = entities) }

	override fun play(vararg locations: Location?): Unit =
		effects.forEach { effect -> effect.play(locations = locations) }

}
