package de.moltenKt.paper.tool.effect

import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.extension.paper.worlds
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntityBasedEffect {

	/**
	 * This function plays the given [EntityBasedEffect] to every player
	 * specified in [entities]. Look at [SoundEffect] for more
	 * enhanced possibilities with sounds.
	 * @param entities The receivers of the effect played.
	 * @author Fruxz
	 * @since 1.0
	 * @see SoundEffect
	 */
	fun play(vararg entities: Entity?)

	fun broadcastPlayers(players: Set<Player> = onlinePlayers.toSet()) =
		play(entities = players.toTypedArray())

	fun broadcastEntities(entities: Set<Entity> = worlds.flatMap { it.entities }.toSet()) =
		play(entities = entities.toTypedArray())

}