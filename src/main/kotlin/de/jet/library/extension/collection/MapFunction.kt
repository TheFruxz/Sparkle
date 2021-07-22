package de.jet.library.extension.collection

fun <T> Iterable<T>.mapToString() =
	map { "$it" }

fun <T> Iterable<T>.mapToInt() =
	map { "$it".toInt() }

fun <T> Iterable<T>.mapToDouble() =
	map { "$it".toDouble() }

fun <T> Iterable<T>.mapToLong() =
	map { "$it".toLong() }

fun <T> Iterable<T>.mapToByte() =
	map { "$it".toByte() }

fun <T> Iterable<T>.mapToBoolean() =
	map { "$it".toBoolean() }

@Suppress("UNCHECKED_CAST")
fun <T, O> Iterable<T>.mapCast() =
	map { it as O }
