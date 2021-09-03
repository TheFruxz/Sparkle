@file:Suppress("UNCHECKED_CAST")

package de.jet.library.extension

fun <O> Any?.forceCast() = this as O

fun <O> Any?.forceCastOrNull() = try {
	this as O
} catch (e: ClassCastException) {
	null
}

fun <O> Any?.forceNullableCast() = this as O?

fun <O> Any?.forceNullableCastOrNull() = try {
	this as O?
} catch (e: ClassCastException) {
	null
}

fun <O> Nothing?.forceCast() = this as O

fun <O> Nothing?.forceCastOrNull() = try {
	this as O
} catch (e: ClassCastException) {
	null
}

fun <O> Nothing?.forceNullableCast() = this as O?

fun <O> Nothing?.forceNullableCastOrNull() = try {
	this
} catch (e: ClassCastException) {
	null
}

fun Any?.dump() = Unit

fun Nothing?.dump() = Unit