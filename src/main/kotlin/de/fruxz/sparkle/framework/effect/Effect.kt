package de.fruxz.sparkle.framework.effect

/**
 * This interface defines an object as an Effect.
 * @author Fruxz
 * @since 1.0
 */
interface Effect

/**
 * This annotation marks an element as part
 * of the Effect-DSL.
 * @author Fruxz
 * @since 1.0
 */
@DslMarker
@MustBeDocumented
annotation class EffectDsl
