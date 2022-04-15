package de.moltenKt.jvm.extension.math

import kotlin.math.abs


/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Int].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Int.difference(o: Int) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Long].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Long.difference(o: Long) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Double].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Double.difference(o: Double) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Float].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Float.difference(o: Float) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Byte].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Byte.difference(o: Byte) = abs(o - this)

/**
 * This function returns the non-negative difference between two numbers.
 * The two numbers are [this] and [o] of type [Short].
 * @param o the other number
 * @return the non-negative difference between [this] and [o]
 * @author Fruxz
 * @since 1.0
 */
fun Short.difference(o: Short) = abs(o - this)