package de.jet.library.extension.collection

fun <T> List<T>.subList(values: IntRange) = subList(values.first, values.last)

fun <O, T : Collection<O>> T.withForEach(action: O.() -> Unit) = forEach(action)

fun <O> Array<O>.withForEach(action: O.() -> Unit) = forEach(action)

fun <I, O, T : Collection<I>> T.withMap(action: I.() -> O) = map(action)

fun <I, O> Array<I>.withMap(action: I.() -> O) = map(action)