package de.jet.library.extension.math

import de.jet.library.tool.math.ModResult

/**
 * Creates a new [ModResult] with the division value and the
 * given numbers [this] and [divide].
 * @param divide The number to divide [this] by.
 * @return The new [ModResult] with the division value.
 * @author Fruxz
 * @since 1.0
 */
infix fun Long.divideResult(divide: Long) = ModResult(this, divide)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Int].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Int.difference(o: Int) = if (o > this) {
	o - this
} else
	this - 0

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Long].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Long.difference(o: Long) = if (o > this) {
	o - this
} else
	this - o

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Double].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Double.difference(o: Double) = if (o > this) {
	o - this
} else
	this - o

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Float].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Float.difference(o: Float) = if (o > this) {
	o - this
} else
	this - o

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Byte].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Byte.difference(o: Byte) = if (o > this) {
	o - this
} else
	this - o

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Short].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Short.difference(o: Short) = if (o > this) {
	o - this
} else
	this - o