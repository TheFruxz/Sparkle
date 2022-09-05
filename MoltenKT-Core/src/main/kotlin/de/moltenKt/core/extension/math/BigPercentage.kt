package de.moltenKt.core.extension.math

infix fun Long.outOf(denominator: Long) = intPercentageOf(this, denominator)

fun intPercentageOf(numerator: Long, denominator: Long) = (numerator * 100.0 / denominator + .5).toInt()
