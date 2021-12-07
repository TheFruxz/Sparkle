package de.jet.jvm.extension.objects

import de.jet.jvm.extension.isNotNull
import org.jetbrains.annotations.NotNull

/**
 * Converts a nullable object/value to a non-null object/value, if it is not null, or
 * if it is null throw a [NoSuchElementException] instead
 * @throws NoSuchElementException
 */
@Throws(NoSuchElementException::class)
@NotNull
fun <T : Any> T?.trust(): T {
	if (isNotNull) {
		return this!!
	} else
		throw NoSuchElementException("element [T] was null, but it is not allowed to be null!")
}

/**
 * Waits, until the element is not null, then returns the not-null it.
 * If it never hits not-null, it never ends!
 */
fun <T> T?.awaitTrust(): T {
	do {
		if (this != null) {
			return this
		}
	} while (this == null)
	throw NullPointerException("AwaitTrust failed")
}