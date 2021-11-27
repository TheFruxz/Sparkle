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