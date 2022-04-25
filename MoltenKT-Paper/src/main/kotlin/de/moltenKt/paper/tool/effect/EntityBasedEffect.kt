package de.moltenKt.paper.tool.effect

import de.moltenKt.paper.tool.effect.sound.SoundEffect
import org.bukkit.entity.Entity

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

}