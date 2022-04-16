package de.moltenKt.core.extension.container

import de.moltenKt.core.extension.forceCast
import java.util.*

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [String]. The
 * backend is converting every element using
 * the internal [toString] method.
 * @param T the type of the original elements
 * @return the list of [String]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToString() =
	map { "$it" }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [String].
 * The backend is converting every element using the internal [toString] method.
 * @param T the type of the original elements
 * @return the list of [String]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToString() =
	map { "$it" }

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [Int] with the
 * [toInt] function, that is attached to a
 * [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Int]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToInt() =
	map { "$it".toInt() }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [Int] with the
 * [toInt] function, that is attached to a [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Int]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToInt() =
	map { "$it".toInt() }

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [Double] with the
 * [toDouble] function, that is attached to a
 * [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Double]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToDouble() =
	map { "$it".toDouble() }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [Double] with the
 * [toDouble] function, that is attached to a [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Double]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToDouble() =
	map { "$it".toDouble() }

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [Long] with the
 * [toLong] function, that is attached to a
 * [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Long]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToLong() =
	map { "$it".toLong() }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [Long] with the
 * [toLong] function, that is attached to a [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Long]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToLong() =
	map { "$it".toLong() }

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [Byte] with the
 * [toByte] function, that is attached to a
 * [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Byte]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToByte() =
	map { "$it".toByte() }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [Byte] with the
 * [toByte] function, that is attached to a [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Byte]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToByte() =
	map { "$it".toByte() }

/**
 * Returns the [Iterable]<[T]> as a [List] where
 * every element is turned to a [Boolean] with the
 * [toBoolean] function, that is attached to a
 * [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Boolean]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Iterable<T>.mapToBoolean() =
	map { "$it".toBoolean() }

/**
 * Returns the [Array]<[T]> as a [List] where every element is turned to a [Boolean] with the
 * [toBoolean] function, that is attached to a [String]-Object only containing the individuell
 * content-entry of [this]
 * @param T the type of the original elements
 * @return the list of [Boolean]s
 * @author Fruxz
 * @since 1.0
 */
fun <T> Array<T>.mapToBoolean() =
	map { "$it".toBoolean() }

/**
 * Cast every element of the [Iterable]<*> to [O]
 * @param O the type of the result elements
 * @return the list of [O] elements
 * @author Fruxz
 * @since 1.0
 */
@Suppress("UNCHECKED_CAST")
fun <O> Iterable<*>.mapCast() =
	map { it as O }

/**
 * Cast every element of the [Array]<*> to [O]
 * @param O the type of the result elements
 * @return the list of [O] elements
 * @author Fruxz
 * @since 1.0
 */
fun <O> Array<*>.mapCast() =
	map { it.forceCast<O>() }

/**
 * This function returns a list of lowercase strings
 * of the original [Iterable]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Iterable<String>.mapToLowercase(locale: Locale = Locale.ROOT) =
	map { it.lowercase(locale) }

/**
 * This function returns a list of lowercase strings
 * of the original [Array]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Array<String>.mapToLowercase(locale: Locale = Locale.ROOT) =
	map { it.lowercase(locale) }

/**
 * This function returns a list of uppercase strings
 * of the original [Iterable]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Iterable<String>.mapToUppercase(locale: Locale = Locale.ROOT) =
	map { it.uppercase(locale) }

/**
 * This function returns a list of uppercase strings
 * of the original [Array]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Array<String>.mapToUppercase(locale: Locale = Locale.ROOT) =
	map { it.uppercase(locale) }
