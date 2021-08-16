package de.jet.library.tool.mutable

import de.jet.app.JetCache
import de.jet.library.tool.smart.Identifiable

class CachedMutable<T>(
	val cacheId: String,
	val default: T
) : FlexibleMutable<T>(default), Identifiable<CachedMutable<T>> {

	init {

		// If not something is already cached, place default
		if (!JetCache.registeredCachedMutables.contains(cacheId)) {
			JetCache.registeredCachedMutables[cacheId] = property
		}

	}

	@Suppress("UNCHECKED_CAST")
	override var property: T
		get() = JetCache.registeredCachedMutables[cacheId] as T ?: default
		set(value) {
			JetCache.registeredCachedMutables[cacheId] = value
		}

	override val identity = cacheId

}
