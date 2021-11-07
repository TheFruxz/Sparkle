package de.jet.library.extension.math

import de.jet.library.tool.math.ModResult

infix fun Long.divideResult(divide: Long) = ModResult(this, divide)

fun Int.difference(o: Int) = if (o > this) {
	o - this
} else
	this - 0

fun Long.difference(o: Long) = if (o > this) {
	o - this
} else
	this - o

fun Double.difference(o: Double) = if (o > this) {
	o - this
} else
	this - o

fun Float.difference(o: Float) = if (o > this) {
	o - this
} else
	this - o

fun Byte.difference(o: Byte) = if (o > this) {
	o - this
} else
	this - o

fun Short.difference(o: Short) = if (o > this) {
	o - this
} else
	this - o