package de.moltenKt.core.annotation

/**
 * This annotation marks every code that is currently untested and
 * have to be tested in the future.
 * The results of the code with this annotation can be wrong or broken.
 * @author Fruxz
 * @since 1.0
 */
@MustBeDocumented
@RequiresOptIn(message = "This feature is marked as 'not tested'!", level = RequiresOptIn.Level.WARNING)
annotation class NotTested
