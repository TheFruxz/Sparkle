package de.fruxz.sparkle.extension.data

import de.fruxz.ascend.tool.mutable.Mutable
import de.fruxz.sparkle.mutable.CachedMutable

fun <T> Mutable.Companion.cached(cacheId: String, value: T) = CachedMutable(cacheId, value)

fun <T> mutableOf(value: T) = Mutable.default(value)

fun <T> mutableCachedOf(cacheId: String, value: T) = Mutable.cached(cacheId, value)