package de.jet.library.extension.data

import de.jet.library.extension.collection.mapToString
import de.jet.library.extension.collection.stackRandom
import kotlin.random.Random
import kotlin.random.nextInt

fun randomBoolean() = (Random.nextInt(1, 2) == 1)

fun randomInt(range: IntRange) = Random.nextInt(range)

fun randomTag(size: Int = 5): String {
	return "#" + (listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z") + (0..9))
		.mapToString()
		.stackRandom(size)
}