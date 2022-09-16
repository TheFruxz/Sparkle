package de.moltenKt.unfold.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import java.util.regex.Pattern

/**
 * Replaces all occurrences of the given [key] with the given [value]
 * and then returns the result component.
 * @param key The key to replace.
 * @param value The value to replace the key with.
 * @return The result component.
 * @author Fruxz
 * @since 1.0
 */
fun <T : Component> T.replace(key: String, value: String): Component =
	replaceText {
		it.match(key)
		it.replacement(value)
	}

/**
 * Replaces all occurrences of the given [key] with the given [value]
 * and then returns the result component.
 * @param key The key to replace.
 * @param value The value to replace the key with.
 * @return The result component.
 * @author Fruxz
 * @since 1.0
 */
fun <T : Component> T.replace(key: String, value: ComponentLike): Component =
	replaceText {
		it.match(key)
		it.replacement(value)
	}

/**
 * Replaces all occurrences of the given [regex] with the given [value]
 * and then returns the result component.
 * @param regex The regex to replace.
 * @param value The value to replace the regex with.
 * @return The result component.
 * @author Fruxz
 * @since 1.0
 */
fun <T : Component> T.replace(regex: Regex, value: String): Component =
	replaceText {
		it.match(Pattern.compile(regex.pattern))
		it.replacement(value)
	}

/**
 * Replaces all occurrences of the given [regex] with the given [value]
 * and then returns the result component.
 * @param regex The regex to replace.
 * @param value The value to replace the regex with.
 * @return The result component.
 * @author Fruxz
 */
fun <T : Component> T.replace(regex: Regex, value: ComponentLike): Component =
	replaceText {
		it.match(Pattern.compile(regex.pattern))
		it.replacement(value)
	}

/**
 * Replaces all occurrences of the given [pattern] with the given [value]
 * and then returns the result component.
 * @param pattern The pattern to replace.
 * @param value The value to replace the pattern with.
 * @return The result component.
 * @author Fruxz
 * @since 1.0
 */
fun <T : Component> T.replace(pattern: Pattern, value: String): Component =
	replaceText {
		it.match(pattern)
		it.replacement(value)
	}

/**
 * Replaces all occurrences of the given [pattern] with the given [value]
 * and then returns the result component.
 * @param pattern The pattern to replace.
 * @param value The value to replace the pattern with.
 * @return The result component.
 * @author Fruxz
 * @since 1.0
 */
fun <T : Component> T.replace(pattern: Pattern, value: ComponentLike): Component =
	replaceText {
		it.match(pattern)
		it.replacement(value)
	}
