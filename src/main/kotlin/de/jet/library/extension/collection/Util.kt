package de.jet.library.extension.collection

fun <T> List<T>.subList(values: IntRange) = subList(values.first, values.last)

fun <O, T : Collection<O>> T.withForEach(action: O.() -> Unit) = forEach(action)

fun <O> Array<O>.withForEach(action: O.() -> Unit) = forEach(action)