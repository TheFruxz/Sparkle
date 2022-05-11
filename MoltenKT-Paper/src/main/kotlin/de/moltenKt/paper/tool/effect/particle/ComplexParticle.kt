package de.moltenKt.paper.tool.effect.particle

import org.bukkit.Location
import org.bukkit.entity.Entity

data class ComplexParticle(
    val effects: Set<ParticleEffect>,
) : ParticleEffect {

    override fun play(vararg entities: Entity?) =
        effects.forEach { it.play(*entities) }

    override fun play(vararg locations: Location?) =
        effects.forEach { it.play(*locations) }

}
