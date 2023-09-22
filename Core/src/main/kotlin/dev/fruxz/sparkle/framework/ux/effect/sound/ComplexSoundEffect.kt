package dev.fruxz.sparkle.framework.ux.effect.sound

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

data class ComplexSoundEffect(
    val effects: Set<SoundEffect>,
) : SoundEffect {
    override fun play(vararg worlds: World?, sticky: Boolean) =
        effects.forEach { it.play(*worlds, sticky = sticky) }

    override fun play(vararg entities: Entity?, sticky: Boolean) =
        effects.forEach { it.play(*entities, sticky = sticky) }

    override fun play(locationSet: Set<Location>, entitySet: Set<Entity>) =
        effects.forEach { it.play(locationSet, entitySet) }

    override fun play(vararg locations: Location?) =
        effects.forEach { it.play(*locations) }

}
