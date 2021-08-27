package de.jet.library.extension.data

import de.jet.library.tool.mutable.Mutable

fun <T> mutableOf(o: T) = Mutable.default(o)