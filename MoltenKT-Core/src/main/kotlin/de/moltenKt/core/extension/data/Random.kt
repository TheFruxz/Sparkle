package de.moltenKt.core.extension.data

import de.moltenKt.core.extension.container.mapToString
import de.moltenKt.core.extension.container.mixedCase
import de.moltenKt.core.extension.container.stackRandom
import de.moltenKt.core.extension.data.RandomTagType.MIXED_CASE
import de.moltenKt.core.extension.data.RandomTagType.ONLY_UPPERCASE
import de.moltenKt.core.extension.switchResult
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Creates a new random boolean, which can be true or false.
 * @param random the randomizer to use.
 * @return a random boolean
 * @author Fruxz
 * @since 1.0
 */
fun randomBoolean(random: Random = Random) = (randomInt(1..2, random) == 1)

/**
 * Creates a new random integer, which is inside the given
 * [range].
 * @param range the range of the random integer (including the boundaries)
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 1.0
 */
fun randomInt(range: IntRange, random: Random = Random) = random.nextInt(range)

/**
 * Creates a new random integer, which is inside the given [progression]
 * @param progression the progression of the random integer (including the boundaries)
 * @param random the randomizer to use
 * @return a random integer
 * @author Fruxz
 * @since 1.0
 */
fun randomInt(progression: IntProgression, random: Random = Random) = progression.toSet().random(random)

/**
 * Creates a new random long, which is inside the given [range].
 * @param range the range of the random long (including the boundaries)
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 1.0
 */
fun randomLong(range: LongRange, random: Random = Random) = random.nextLong(range.first, range.last+1)

/**
 * Creates a new random long, which is inside the given [progression]
 * @param progression the progression of the random long (including the boundaries)
 * @param random the randomizer to use
 * @return a random long
 * @author Fruxz
 * @since 1.0
 */
fun randomLong(progression: LongProgression, random: Random = Random) = progression.toSet().random(random)

/**
 * Creates a random Tag, which is a combination of a '#' and some
 * random letters & numbers.
 * The template of the Tag is '#[letters & numbers]'.
 * @param size the amount of mixed letters & numbers, which the Tag should have ([size]+1 == tag.length)
 * @param hashtag if true, the Tag will start with a '#'
 * @param tagType the type of the Tag creation (if uppercase, lowercase or mixed-case)
 * @param stackRandomizer the randomizer, which is used to shuffle the characters of the Tag
 * @return a random Tag with the # at the start of the generated Tag
 * @author Fruxz
 * @since 1.0
 */
fun buildRandomTag(size: Int = 5, hashtag: Boolean = true, tagType: RandomTagType = ONLY_UPPERCASE, stackRandomizer: Random = Random(Random.nextLong())): String {
	var letters = "abcdefghijklmnopqrstuvwxyz"

	if (tagType == ONLY_UPPERCASE) letters = letters.uppercase()
	if (tagType == MIXED_CASE) letters = letters.mixedCase()

	return hashtag.switchResult("#", "") + (letters.toCharArray().toList() + (0..9))
		.mapToString()
		.stackRandom(size, stackRandomizer)
}

enum class RandomTagType {
	ONLY_UPPERCASE,
	ONLY_LOWERCASE,
	MIXED_CASE;
}

fun buildRandomSeeds(amount: Long, random: Random = Random) = buildSet {
	for (i in 1..amount) {
		add(random.nextLong())
	}
}
