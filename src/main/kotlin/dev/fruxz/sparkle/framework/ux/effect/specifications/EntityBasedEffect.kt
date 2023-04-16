package dev.fruxz.sparkle.framework.ux.effect.specifications

import dev.fruxz.sparkle.framework.system.onlinePlayers
import dev.fruxz.sparkle.framework.ux.effect.Effect
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntityBasedEffect : Effect {

    fun play(vararg entities: Entity?)

    fun broadcastPlayers(players: Set<Player> = onlinePlayers) = play(*players.toTypedArray())

    fun broadcastEntities(entities: Set<Entity>) = play(*entities.toTypedArray())

}