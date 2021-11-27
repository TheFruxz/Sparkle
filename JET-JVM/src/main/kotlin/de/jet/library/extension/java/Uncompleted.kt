package de.jet.library.extension.java

import de.jet.library.extension.isNotNull
import java.util.*
import kotlin.NoSuchElementException

/**
 * Returns the value of the [Optional] or null if the optional
 * throws a [NoSuchElementException]
 * @return the value of the [Optional] or null
 * @author Fruxz
 * @since 1.0
 */
fun <T> Optional<T>.getOrNull() = try {
    get()
} catch (e: NoSuchElementException) {
    null
}

/**
 * Returns the value of the [Optional] or null if the [Optional]
 * is null or if the [Optional] throws a [NoSuchElementException].
 * @return the value of the [Optional] or null
 * @author Fruxz
 * @since 1.0
 */
@JvmName("nullableGetOrNullT")
fun <T> Optional<T>?.getOrNull() = try {
    if (isNotNull) {
        this!!.get()
    } else
        null
} catch (e: NoSuchElementException) {
    null
}

/**
 * Returns the value of the [Optional] or the [default] parameter
 * if the [Optional] throws a [NoSuchElementException]
 * @param default the default value that gets returned if the [Optional] throws the [NoSuchElementException]
 * @return the value of the [Optional] or [default]
 * @author Fruxz
 * @since 1.0
 */
fun <T> Optional<T>.getOrDefault(default: T) = getOrNull() ?: default

/**
 * Returns the value of the [Optional] or the [default] parameter
 * if the [Optional] is null or if the [Optional] throws a [NoSuchElementException].
 * @param default the default value that gets returned if null or if the [Optional] throws the [NoSuchElementException]
 * @return the value of the [Optional] or [default]
 * @author Fruxz
 * @since 1.0
 */
@JvmName("nullableGetOrDefaultT")
fun <T> Optional<T>?.getOrDefault(default: T) = getOrNull() ?: default