package de.moltenKt.paper.tool.effect

import de.moltenKt.paper.tool.effect.sound.SoundEffect
import org.bukkit.entity.Entity

interface EntityBasedEffect {

	/**
	 * This function plays the given [SoundEffect] to every player
	 * specified in [entities]. Look at [SoundEffect] for more
	 * enhanced possibilities.
	 * @param entities The receivers of the sound played.
	 * @author Fruxz
	 * @since 1.0
	 * @see SoundEffect
	 */
	fun play(vararg entities: Entity?)

}