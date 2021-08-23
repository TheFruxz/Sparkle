package de.jet.library.extension.data

import java.text.DecimalFormat

fun String?.isInt() = try {
	if (this != null) {
		toInt()
		true
	} else false
} catch (e: NumberFormatException) {
	false
}

fun String?.isDouble() = try {
	if (this != null) {
		toDouble()
		true
	} else false
} catch (e: NumberFormatException) {
	false
}

fun String?.isFloat() = try {
	if (this != null) {
		toFloat()
		true
	} else false
} catch (e: NumberFormatException) {
	false
}

fun String?.isLong() = try {
	if (this != null) {
		toLong()
		true
	} else false
} catch (e: NumberFormatException) {
	false
}

fun String?.isByte() = try {
	if (this != null) {
		toByte()
		true
	} else false
} catch (e: NumberFormatException) {
	false
}

fun String?.isBoolean() = this?.toBooleanStrictOrNull() != null

fun Number.format(pattern: String): String = DecimalFormat(pattern).format(this)

val Number.shorter: String
	get() = this.format("##.##")

operator fun IntArray.plus(intArray: IntArray) = asIterable().toList().toTypedArray() + intArray.toList()

fun Int.maxedOut(maximum: Int) = this.let { if (it <= maximum) it else maximum }

fun Int.minedOut(minimum: Int) = this.let { if (it >= minimum) it else minimum }
