package de.fruxz.sparkle.framework.util.effect

import org.bukkit.Location

/**
 * This interface defines an Object to be an Effect,
 * which is dependent on Locations.
 * @author Fruxz
 * @since 1.0
 */
interface LocationBasedEffect : Effect {

	/**
	 * This function plays/executes the effect with the
	 * given [locations] as the target location.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun play(vararg locations: Location?)

}