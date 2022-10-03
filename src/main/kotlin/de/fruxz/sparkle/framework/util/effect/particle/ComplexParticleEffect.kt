package de.fruxz.sparkle.framework.util.effect.particle

import org.bukkit.Location
import org.bukkit.entity.Entity

data class ComplexParticleEffect(
	val effects: Set<ParticleEffect>,
) : ParticleEffect {

    override fun play(vararg entities: Entity?): Unit =
        effects.forEach { it.play(*entities) }

    override fun play(vararg locations: Location?): Unit =
        effects.forEach { it.play(*locations) }

    override fun play(): Unit =
        effects.forEach(ParticleEffect::play)

    override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
        effects.forEach { effect -> effect.play(locations, entities) }

}
