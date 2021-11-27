package de.jet.library.extension.data

import de.jet.library.tool.mutable.Mutable

/**
 * Creates a new mutable object using the [Mutable.default] function.
 * It accepts a [T] as the default value of the mutable.
 * @param o the default value of the mutable
 * @param T the inner type of the mutable
 * @return a new mutable object containing the default value
 * @author Fruxz
 * @since 1.0
 */
fun <T> mutableOf(o: T) = Mutable.default(o)

/**
 * This function increases the int value of the [Mutable]<[Int]> by 1 int.
 * @return the [Mutable]<[Int]> with the value increased by 1
 * @author Fruxz
 * @since 1.0
 */
operator fun <T : Mutable<Int>> T.inc(): T { return apply { property += 1 } }

/**
 * This function decreased the int value of the [Mutable]<[Int]> by 1 int.
 * @return the [Mutable]<[Int]> with the value decreased by 1
 * @author Fruxz
 * @since 1.0
 */
operator fun <T : Mutable<Int>> T.dec(): T { return apply { property -= 1 } }