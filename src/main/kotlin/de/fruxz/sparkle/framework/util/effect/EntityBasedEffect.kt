package de.fruxz.sparkle.framework.util.effect

import de.fruxz.sparkle.framework.util.extension.onlinePlayers
import de.fruxz.sparkle.framework.util.extension.worlds
import de.fruxz.sparkle.framework.util.effect.sound.SoundEffect
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

/**
 * This interface defines an object as an [Effect], which targets to be
 * used at an Entity (or player, which is an Entity).
 * @author Fruxz
 * @since 1.0
 */
interface EntityBasedEffect : Effect {

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

	/**
	 * This function 'broadcasts' this [EntityBasedEffect] to the [players],
	 * which are by default every [onlinePlayers]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun broadcastPlayers(players: Set<Player> = onlinePlayers) =
		play(entities = players.toTypedArray())

	/**
	 * This function 'broadcasts' this [EntityBasedEffect] to the [entities],
	 * which are by default every [Entity] on every loaded world inside [worlds].
	 * @author Fruxz
	 * @since 1.0
	 */
	fun broadcastEntities(entities: Set<Entity> = worlds.flatMap { it.entities }.toSet()) =
		play(entities = entities.toTypedArray())

}