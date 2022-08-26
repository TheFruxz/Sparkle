package de.moltenKt.core.extension.math

import java.util.DoubleSummaryStatistics
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * This function executes the [ceil] function on the given [x]
 * and returns the result as a [Int].
 * @param x The value to round up.
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
fun ceilToInt(x: Double) = ceil(x).toInt()

/**
 * This function executes the [ceil] function on the given [x]
 * and returns the result as a [Int].
 * @param x The value to round up.
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
fun ceilToInt(x: Float) = ceil(x).toInt()

/**
 * This function executes the [ceil] function on [this] given [Double]
 * and returns the result as a [Int].
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
@JvmName("ceilToIntThis")
fun Double.ceilToInt() = ceil(this).toInt()

/**
 * This function executes the [ceil] function on [this] given [Float]
 * and returns the result as a [Int].
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
@JvmName("ceilToIntThis")
fun Float.ceilToInt() = ceil(this).toInt()

/**
 * This function executes the [ceil] function on [this] given [Double]
 * and returns the result as a [Double].
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
fun Double.ceil() = ceil(this)

/**
 * This function executes the [ceil] function on [this] given [Float]
 * and returns the result as a [Float].
 * @return The rounded up value.
 * @see ceil
 * @author Fruxz
 * @since 1.0
 */
fun Float.ceil() = ceil(this)

/**
 * This function executes the [floor] function on the given [x]
 * and returns the result as a [Int].
 * @param x The value to round down.
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
fun floorToInt(x: Double) = floor(x).toInt()

/**
 * This function executes the [floor] function on the given [x]
 * and returns the result as a [Int].
 * @param x The value to round down.
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
fun floorToInt(x: Float) = floor(x).toInt()

/**
 * This function executes the [floor] function on [this] given [Double]
 * and returns the result as a [Int].
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
@JvmName("floorToIntThis")
fun Double.floorToInt() = floor(this).toInt()

/**
 * This function executes the [floor] function on [this] given [Float]
 * and returns the result as a [Int].
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
@JvmName("floorToIntThis")
fun Float.floorToInt() = floor(this).toInt()

/**
 * This function executes the [floor] function on [this] given [Double]
 * and returns the result as a [Double].
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
fun Double.floor() = floor(this)

/**
 * This function executes the [floor] function on [this] given [Float]
 * and returns the result as a [Float].
 * @return The rounded down value.
 * @see floor
 * @author Fruxz
 * @since 1.0
 */
fun Float.floor() = floor(this)

/**
 * This function returns the [x] double, but rounded with [decimals]-amount of decimals.
 * @param x The value to round.
 * @param decimals The amount of decimals to round to.
 * @return The rounded value.
 * @author Fruxz
 * @since 1.0
 */
fun round(x: Double, decimals: Int): Double = 10.0.pow(decimals).let { factor -> round(x * factor) / factor }

/**
 * This function returns [this] double, but rounded with [decimals]-amount of decimals.
 * @param decimals The amount of decimals to round to.
 * @return The rounded value.
 * @author Fruxz
 * @since 1.0
 */
@JvmName("roundWithDecimalsThis")
fun Double.round(decimals: Int): Double = round(this, decimals)

/**
 * This function returns the [x] float, but rounded with [decimals]-amount of decimals.
 * @param x The value to round.
 * @param decimals The amount of decimals to round to.
 * @return The rounded value.
 * @author Fruxz
 * @since 1.0
 */
fun round(x: Float, decimals: Int): Float = 10.0.pow(decimals).toFloat().let { factor -> round(x * factor) / factor }

/**
 * This function returns [this] float, but rounded with [decimals]-amount of decimals.
 * @param decimals The amount of decimals to round to.
 * @return The rounded value.
 * @author Fruxz
 * @since 1.0
 */
@JvmName("roundWithDecimalsThis")
fun Float.round(decimals: Int): Float = round(this, decimals)