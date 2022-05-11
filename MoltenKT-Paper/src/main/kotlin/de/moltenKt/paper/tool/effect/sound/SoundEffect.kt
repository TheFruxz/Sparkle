package de.moltenKt.paper.tool.effect.sound

import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.tool.effect.CrossBasedEffect
import org.bukkit.World
import org.bukkit.entity.Entity

interface SoundEffect : CrossBasedEffect {

	/**
	 * This function plays the given [SoundEffect] to every player
	 * in the given [worlds]. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param worlds The worlds to play the sound in.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun play(vararg worlds: World?, sticky: Boolean = true)

	/**
	 * This function plays the given [SoundEffect] to every player
	 * specified in [entities]. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param entities The receivers of the sound played.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun play(vararg entities: Entity?, sticky: Boolean)

	override fun play(vararg entities: Entity?): Unit =
		play(*entities, sticky = true)

	/**
	 * This function plays the given [SoundEffect] to every player
	 * online on the server. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun broadcast(sticky: Boolean = true): Unit =
		play(*onlinePlayers.toTypedArray(), sticky = sticky)

}