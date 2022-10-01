package de.moltenKt.paper.tool.effect

/**
 * This interface defines an object as an effect,
 * which is not dependent on a target, to be
 * displayed. The effect can be played, without
 * having to get a location, entity, or something
 * similar.
 * @author Fruxz
 * @since 1.0
 */
interface IndependentEffect : Effect {

    /**
     * This function plays/executes the effect, without the need of
     * some additional parameters, because the effect is/can be independent.
     * @author Fruxz
     * @since 1.0
     */
    fun play()

}