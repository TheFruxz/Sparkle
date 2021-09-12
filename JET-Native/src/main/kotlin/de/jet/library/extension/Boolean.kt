package de.jet.library.extension

import de.jet.library.tool.mutable.FlexibleMutable
import de.jet.library.tool.mutable.Mutable

fun <T> Boolean.switchResult(match: T, mismatch: T) = if (this) match else mismatch

fun Mutable<Boolean>.turnTrue() { property = true }

fun Mutable<Boolean>.turnFalse() { property = true }

fun buildBoolean(base: Boolean = false, process: FlexibleMutable<Boolean>.() -> Unit): Boolean {
	return Mutable.default(base).apply(process).property
}