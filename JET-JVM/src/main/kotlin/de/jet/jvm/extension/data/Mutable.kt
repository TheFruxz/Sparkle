package de.jet.jvm.extension.data

import de.jet.jvm.tool.mutable.Mutable

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
 * This function takes the current object [T] and returns a new mutable object,
 * which contains the same value as the current object.
 * @param T the inner type of the mutable
 * @return a new mutable object containing the same value as the current object
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any?> T.toMutable() = mutableOf(this)

fun <T> computationalMutableOf(o: T, onSet: (T) -> Unit, onGet: () -> T) = Mutable.computational(o, onSet, onGet)

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

/**
 * This function increases the int value of the [Mutable]<[Long]> by 1 int.
 * @return the [Mutable]<[Long]> with the value increased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("incLong")
operator fun <T : Mutable<Long>> T.inc(): T { return apply { property += 1 } }

/**
 * This function decreased the int value of the [Mutable]<[Long]> by 1 int.
 * @return the [Mutable]<[Long]> with the value decreased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("decLong")
operator fun <T : Mutable<Long>> T.dec(): T { return apply { property -= 1 } }

/**
 * This function increases the int value of the [Mutable]<[Float]> by 1 int.
 * @return the [Mutable]<[Float]> with the value increased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("incFloat")
operator fun <T : Mutable<Float>> T.inc(): T { return apply { property += 1 } }

/**
 * This function decreased the int value of the [Mutable]<[Float]> by 1 int.
 * @return the [Mutable]<[Float]> with the value decreased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("decFloat")
operator fun <T : Mutable<Float>> T.dec(): T { return apply { property -= 1 } }

/**
 * This function increases the int value of the [Mutable]<[Double]> by 1 int.
 * @return the [Mutable]<[Double]> with the value increased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("incDouble")
operator fun <T : Mutable<Double>> T.inc(): T { return apply { property += 1 } }

/**
 * This function decreased the int value of the [Mutable]<[Double]> by 1 int.
 * @return the [Mutable]<[Double]> with the value decreased by 1
 * @author Fruxz
 * @since 1.0
 */
@JvmName("decDouble")
operator fun <T : Mutable<Double>> T.dec(): T { return apply { property -= 1 } }
