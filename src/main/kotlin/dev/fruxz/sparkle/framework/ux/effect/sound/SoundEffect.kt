package dev.fruxz.sparkle.framework.ux.effect.sound

import dev.fruxz.sparkle.framework.system.onlinePlayers
import dev.fruxz.sparkle.framework.ux.effect.combinations.CrossBasedEffect
import org.bukkit.World
import org.bukkit.entity.Entity

interface SoundEffect : CrossBasedEffect {

    fun play(vararg worlds: World?, sticky: Boolean = true)

    fun play(vararg entities: Entity?, sticky: Boolean = true)

    override fun play(vararg entities: Entity?) = play(*entities, sticky = true)

    fun broadcast(sticky: Boolean = true, receivers: Set<Entity> = onlinePlayers) = play(*receivers.toTypedArray(), sticky = sticky)

}