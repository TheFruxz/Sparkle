package de.jet.jvm.extension.math

import de.jet.jvm.tool.math.Percentage
import java.text.DecimalFormat
import kotlin.DeprecationLevel.ERROR

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
@Deprecated("Use maxTo() function instead.", ReplaceWith("maxTo(maximum)"), ERROR)
fun Int.maxedOut(maximum: Int) = maxTo(maximum)

/**
 * Returns the [this] [Int] but it cannot be smaller than the [minimum] number,
 * so it will be set to the [minimum] number if it is smaller.
 * @param minimum the minimum number.
 * @return the [this] [Int] but it cannot be smaller than the [minimum] number.
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("Use minTo() function instead.", ReplaceWith("minTo(minimum)"), ERROR)
fun Int.minedOut(minimum: Int) = minTo(minimum)

// max functions

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Byte.maxTo(maximum: Byte) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Short.maxTo(maximum: Short) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Int.maxTo(maximum: Int) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Long.maxTo(maximum: Long) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Float.maxTo(maximum: Float) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Double.maxTo(maximum: Double) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UByte.maxTo(maximum: UByte) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UShort.maxTo(maximum: UShort) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UInt.maxTo(maximum: UInt) = let { if (it <= maximum) it else maximum }

/**
 * This function limits [this] number to the [maximum] number.
 * @param maximum the maximum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun ULong.maxTo(maximum: ULong) = let { if (it <= maximum) it else maximum }

// min functions

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Byte.minTo(minimum: Byte) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Short.minTo(minimum: Short) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Int.minTo(minimum: Int) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Long.minTo(minimum: Long) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Float.minTo(minimum: Float) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun Double.minTo(minimum: Double) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UByte.minTo(minimum: UByte) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UShort.minTo(minimum: UShort) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun UInt.minTo(minimum: UInt) = let { if (it >= minimum) it else minimum }

/**
 * This function limits [this] number to the [minimum] number.
 * @param minimum the minimum size of the number.
 * @author Fruxz
 * @since 1.0
 */
fun ULong.minTo(minimum: ULong) = let { if (it >= minimum) it else minimum }

// limit range functions

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Byte.limitTo(range: IntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Short.limitTo(range: IntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Int.limitTo(range: IntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Long.limitTo(range: LongRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Float.limitTo(range: ClosedFloatingPointRange<Float>) = let { if (it in range) it else range.start }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Double.limitTo(range: ClosedFloatingPointRange<Double>) = let { if (it in range) it else range.start }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UByte.limitTo(range: UIntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UShort.limitTo(range: UIntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UInt.limitTo(range: UIntRange) = let { if (it in range) it else range.first }

/**
 * This function limits [this] number to the [range] number range.
 * @param range the range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun ULong.limitTo(range: ULongRange) = let { if (it in range) it else range.first }

// limit progression functions

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Byte.limitTo(progression: IntProgression) = let { if (it.toInt() in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Short.limitTo(progression: IntProgression) = let { if (it.toInt() in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Int.limitTo(progression: IntProgression) = let { if (it in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun Long.limitTo(progression: LongProgression) = let { if (it in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UByte.limitTo(progression: UIntProgression) = let { if (it.toUInt() in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UShort.limitTo(progression: UIntProgression) = let { if (it.toUInt() in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun UInt.limitTo(progression: UIntProgression) = let { if (it in progression) it else progression.first }

/**
 * This function limits [this] number to the [progression] number range.
 * @param progression the allowed range, where the number will be limited.
 * @author Fruxz
 * @since 1.0
 */
fun ULong.limitTo(progression: ULongProgression) = let { if (it in progression) it else progression.first }

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
