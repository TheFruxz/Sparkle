package de.jet.jvm.extension.math

import de.jet.jvm.tool.math.Percentage
import java.text.DecimalFormat

/**
 * Returns if the string can be parsed to a [Int].
 * @return true if the string can be parsed to a [Int], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isInt() = this?.toIntOrNull() != null

/**
 * Returns if the string can be parsed to a [Double].
 * @return true if the string can be parsed to a [Double], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isDouble() = this?.toDoubleOrNull() != null

/**
 * Returns if the string can be parsed to a [Float].
 * @return true if the string can be parsed to a [Float], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isFloat() = this?.toFloatOrNull() != null

/**
 * Returns if the string can be parsed to a [Long].
 * @return true if the string can be parsed to a [Long], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isLong() = this?.toLongOrNull() != null

/**
 * Returns if the string can be parsed to a [Byte].
 * @return true if the string can be parsed to a [Byte], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isByte() = this?.toByteOrNull() != null

/**
 * Returns if the string can be parsed to a [Boolean].
 * @return true if the string can be parsed to a [toBoolean], otherwise false.
 * @author Fruxz
 * @since 1.0
 */
fun String?.isBoolean() = this?.toBooleanStrictOrNull() != null

/**
 * Formats the number to the specific [pattern] using a [DecimalFormat] with
 * its internal [format] function and the [this] number.
 * @param pattern the pattern to format the number with.
 * @return the formatted number as a string.
 * @author Fruxz
 * @since 1.0
 */
fun Number.format(pattern: String): String = DecimalFormat(pattern).format(this)

/**
 * Formats the number to the '##.##' pattern using the [Number.format] function.
 * @return the formatted number as a string.
 * @author Fruxz
 * @since 1.0
 */
val Number.shorter: String
	get() = this.format("##.##")

/**
 * Adds the [intArray] to the [this] [IntArray] merging into a new array containing both.
 * @param intArray the array to add to the [this] array.
 * @return the merged array.
 * @author Fruxz
 * @since 1.0
 */
operator fun IntArray.plus(intArray: IntArray) = asIterable().toList().toTypedArray() + intArray.toList()

/**
 * Returns the [this] [Int] but it cannot be larger than the [maximum] number,
 * so it will be set to the [maximum] number if it is larger.
 * @param maximum the maximum number.
 * @return the [this] [Int] but it cannot be larger than the [maximum] number.
 * @author Fruxz
 * @since 1.0
 */
fun Int.maxedOut(maximum: Int) = this.let { if (it <= maximum) it else maximum }

/**
 * Returns the [this] [Int] but it cannot be smaller than the [minimum] number,
 * so it will be set to the [minimum] number if it is smaller.
 * @param minimum the minimum number.
 * @return the [this] [Int] but it cannot be smaller than the [minimum] number.
 * @author Fruxz
 * @since 1.0
 */
fun Int.minedOut(minimum: Int) = this.let { if (it >= minimum) it else minimum }

/**
 * Creates a [Percentage] object with the [this] [Double]
 * as a decimal number.
 * @author Fruxz
 * @since 1.0
 */
val Double.decimalAsPercent: Percentage
    get() = Percentage(this)

/**
 * Creates a [Percentage] object with the [this] [Double]
 * as a decimal number.
 * @author Fruxz
 * @since 1.0
 */
val Float.decimalAsPercent: Percentage
    get() = Percentage(this.toDouble())
