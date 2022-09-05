package de.moltenKt.core.extension.container

import kotlin.math.absoluteValue

/**
 * Removes every element from this collection that is contained in the [ints] collection.
 * @param ints the collection of ints to remove
 * @return the elements of the range minus the elements of the [ints] collection
 * @author Fruxz
 * @since 1.0
 */
fun IntRange.skip(vararg ints: Int) =
	toMutableList().apply { removeAll(ints.toList().toSet()) }

/**
 * Removes the [int] from this range.
 * @param int the int to remove
 * @return the elements of the range minus the [int]
 * @author Fruxz
 * @since 1.0
 */
infix fun IntRange.skip(int: Int) =
	skip(ints = intArrayOf(int))

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 1.0
 */
val Iterable<Int>.span: Int
	get() = (max() - min()).absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 1.0
 */
val Iterable<Long>.span: Long
	get() = (max() - min()).absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 1.0
 */
val Iterable<Float>.span: Float
	get() = (max() - min()).absoluteValue

/**
 * This computational value returns the span between the [min]
 * and [max] value of this [Iterable].
 * @author Fruxz
 * @since 1.0
 */
val Iterable<Double>.span: Double
	get() = (max() - min()).absoluteValue