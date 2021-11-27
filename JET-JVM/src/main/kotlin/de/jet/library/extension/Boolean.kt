package de.jet.library.extension

import de.jet.library.tool.mutable.FlexibleMutable
import de.jet.library.tool.mutable.Mutable

/**
 * This functions returns [match] if [this] is true, otherwise [mismatch].
 * @param match the result if [this] is true
 * @param mismatch the result if [this] is false
 * @return [match] if [this] is true, otherwise [mismatch]
 * @author Fruxz
 * @since 1.0
 */
fun <T> Boolean.switchResult(match: T, mismatch: T) = if (this) match else mismatch

/**
 * This functions sets the value of a [Mutable]<[Boolean]> to true
 * @author Fruxz
 * @since 1.0
 */
fun Mutable<Boolean>.turnTrue() { property = true }

/**
 * This functions sets the value of a [Mutable]<[Boolean]> to false
 * @author Fruxz
 * @since 1.0
 */
fun Mutable<Boolean>.turnFalse() { property = false }

/**
 * This function builds a new boolean using internally a mutable boolean but
 * returns the value of the mutable boolean at the end.
 * @param base the first value of the mutable boolean
 * @param process the edit process of the mutable boolean
 * @return the value of the mutable boolean at the end
 * @author Fruxz
 * @since 1.0
 */
fun buildBoolean(base: Boolean = false, process: FlexibleMutable<Boolean>.() -> Unit): Boolean {
	return Mutable.default(base).apply(process).property
}