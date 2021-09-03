package de.jet.library.extension.data

import de.jet.library.tool.mutable.Mutable

fun <T> mutableOf(o: T) = Mutable.default(o)

operator fun <T : Mutable<Int>> T.inc(): T { return apply { property += 1 } }

operator fun <T : Mutable<Int>> T.dec(): T { return apply { property -= 1 } }