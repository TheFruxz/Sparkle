package de.jet.jvm.annotation

/**
 * This annotation marks ever code that is currently not working,
 * the code with this annotation can throw exceptions, do not return
 * the wright stuff or/and complete misses the target.
 * @author Fruxz
 * @since 1.0
 */
@RequiresOptIn(message = "This feature is marked as 'not working'!", level = RequiresOptIn.Level.ERROR)
annotation class NotWorking
