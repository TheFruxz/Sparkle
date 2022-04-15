package de.moltenKt.paper.extension.data

import de.moltenKt.jvm.tool.mutable.Mutable
import de.moltenKt.paper.mutable.CachedMutable

fun <T> Mutable.Companion.cached(cacheId: String, value: T) = CachedMutable(cacheId, value)

fun <T> mutableOf(value: T) = Mutable.default(value)

fun <T> mutableCachedOf(cacheId: String, value: T) = Mutable.cached(cacheId, value)