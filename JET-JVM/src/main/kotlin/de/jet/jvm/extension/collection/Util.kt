package de.jet.jvm.extension.collection

/**
 * Creates a sublist of [this] List<[T]> using the [subList] function
 * but accept a [IntRange] as range instead.
 * @param values the [IntRange] to use as take parameter
 * @return the sublist of [this] List<[T]>
 * @author Fruxz
 * @since 1.0
 */
fun <T> List<T>.subList(values: IntRange) = subList(values.first, values.last)

/**
 * [forEach] a [Collection] but instead of a 'it' lambda it uses a 'this' lambda,
 * which is the same as [forEach] a [Collection] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @author Fruxz
 * @since 1.0
 */
fun <O, T : Collection<O>> T.withForEach(action: O.() -> Unit) = forEach(action)

/**
 * [forEach] a [Array] but instead of a 'it' lambda it uses a 'this' lambda,
 * which is the same as [forEach] a [Array] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @author Fruxz
 * @since 1.0
 */
fun <O> Array<O>.withForEach(action: O.() -> Unit) = forEach(action)

/**
 * Maps a [Collection] to a [List] using the [map] function, which
 * is the same as [map] a [Collection] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List]
 * @author Fruxz
 * @since 1.0
 */
fun <I, O, T : Collection<I>> T.withMap(action: I.() -> O) = map(action)

/**
 * Maps a [Array] to a [List] using the [map] function, which
 * is the same as [map] a [Array] with the [with] function inside.
 * @param action the action in the 'this' perspective
 * @return the mapped [List]
 * @author Fruxz
 * @since 1.0
 */
fun <I, O> Array<I>.withMap(action: I.() -> O) = map(action)