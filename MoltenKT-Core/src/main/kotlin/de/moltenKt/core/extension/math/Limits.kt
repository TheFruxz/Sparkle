package de.moltenKt.core.extension.math

/**
 * Limits [this] to the given [range]. If you want it to something like a [IntProgression],
 * use the [limitToIterable] function instead!
 * @param range the range to limit [this] to.
 * @return [this], or [ClosedRange.start] if [this] is smaller than [ClosedRange.start], or [ClosedRange.endInclusive] if [this] is bigger than [ClosedRange.endInclusive].
 * @author Fruxz
 * @since 1.0
 */
fun <C : Comparable<C>> C.limitTo(range: ClosedRange<C>) = let { if (it in range) it else if (it > range.endInclusive) range.endInclusive else range.start }

/**
 * Limits [this] to the given [range]. If you want it to something like a [ClosedRange],
 * use the [limitTo] function instead!
 * @param range the range to limit [this] to.
 * @author Fruxz
 * @since 1.0
 */
fun <C : Iterable<T>, T : Comparable<T>> T.limitToIterable(range: C) = let { if (it in range) it else if (it > range.max()) range.max() else range.min() }

/**
 * Limits [this] to the minimum of [minimum].
 * @param minimum the minimum to limit [this] to.
 * @return [this], or [minimum] if [this] is smaller than [minimum].
 * @author Fruxz
 * @since 1.0
 */
fun <C : Comparable<C>> C.minTo(minimum: C) = let { if (it >= minimum) it else minimum }

/**
 * Limits [this] to the maximum of [maximum].
 * @param maximum the maximum to limit [this] to.
 * @return [this], or [maximum] if [this] is bigger than [maximum].
 * @author Fruxz
 * @since 1.0
 */
fun <C : Comparable<C>> C.maxTo(maximum: C) = let { if (it <= maximum) it else maximum }