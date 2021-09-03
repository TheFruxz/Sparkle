package de.jet.minecraft.extension.data

import de.jet.library.tool.mutable.Mutable
import de.jet.minecraft.mutable.CachedMutable

fun <T> Mutable.Companion.cached(cacheId: String, value: T) = CachedMutable(cacheId, value)