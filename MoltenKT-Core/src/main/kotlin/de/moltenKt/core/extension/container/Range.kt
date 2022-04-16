package de.moltenKt.core.extension.container

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