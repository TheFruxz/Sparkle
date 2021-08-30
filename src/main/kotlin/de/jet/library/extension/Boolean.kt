package de.jet.library.extension

fun <T> Boolean.switchResult(match: T, mismatch: T) = if (this) match else mismatch