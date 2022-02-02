package de.jet.jvm.extension.data

import de.jet.jvm.extension.collection.mapToString
import de.jet.jvm.extension.collection.stackRandom
import de.jet.jvm.extension.data.RandomTagType.MIXED_CASE
import de.jet.jvm.extension.data.RandomTagType.ONLY_UPPERCASE
import de.jet.jvm.extension.switchResult
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Creates a new random boolean, which can be true or false.
 * @return a random boolean
 * @author Fruxz
 * @since 1.0
 */
fun randomBoolean() = (Random.nextInt(1, 2) == 1)

/**
 * Creates a new random integer, which is inside the given
 * [range].
 * @param range the range of the random integer (including the boundaries)
 * @return a random integer
 * @author Fruxz
 * @since 1.0
 */
fun randomInt(range: IntRange) = Random.nextInt(range)

/**
 * Creates a random Tag, which is a combination of a '#' and some
 * random letters & numbers.
 * The template of the Tag is '#[letters & numbers]'.
 * @param size the amount of mixed letters & numbers, which the Tag should have ([size]+1 == tag.length)
 * @param hashtag if true, the Tag will start with a '#'
 * @param tagType the type of the Tag creation (if uppercase, lowercase or mixed-case)
 * @return a random Tag with the # at the start of the generated Tag
 * @author Fruxz
 * @since 1.0
 */
fun buildRandomTag(size: Int = 5, hashtag: Boolean = true, tagType: RandomTagType = ONLY_UPPERCASE): String {
	val letters = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
		.map { when (tagType) {
			ONLY_UPPERCASE -> it.uppercase()
			MIXED_CASE -> (0..1).random().let { r -> if (r == 0) it.uppercase() else it }
			else -> it
		} }

	return hashtag.switchResult("#", "") + (letters + (0..9))
		.mapToString()
		.stackRandom(size)
}

enum class RandomTagType {
	ONLY_UPPERCASE,
	ONLY_LOWERCASE,
	MIXED_CASE;
}