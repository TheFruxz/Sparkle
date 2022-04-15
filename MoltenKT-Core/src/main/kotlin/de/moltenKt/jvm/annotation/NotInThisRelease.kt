package de.moltenKt.jvm.annotation

/**
 * This annotation is used to mark a class as not being part of the current release feature set.
 */
@MustBeDocumented
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
internal annotation class NotInThisRelease
