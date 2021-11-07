package de.jet.library.extension.collection

fun IntRange.skip(vararg ints: Int) =
	toMutableList().apply { removeAll(ints.toList()) }

infix fun IntRange.skip(int: Int) =
	skip(ints = intArrayOf(int))