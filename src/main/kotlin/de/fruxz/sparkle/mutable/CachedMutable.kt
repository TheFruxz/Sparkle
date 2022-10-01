package de.fruxz.sparkle.mutable

import de.fruxz.ascend.tool.mutable.FlexibleMutable
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.app.SparkleCache

class CachedMutable<T>(
	val cacheId: String,
	val default: T
) : FlexibleMutable<T>(default), Identifiable<CachedMutable<T>> {

	init {

		// If not something is already cached, place default
		if (!SparkleCache.registeredCachedMutables.contains(cacheId)) {
			SparkleCache.registeredCachedMutables += cacheId to property
		}

	}

	@Suppress("UNCHECKED_CAST")
	override var property: T
		get() = SparkleCache.registeredCachedMutables[cacheId] as T ?: default
		set(value) {
			SparkleCache.registeredCachedMutables += cacheId to value
		}

	override val identity = cacheId

}
