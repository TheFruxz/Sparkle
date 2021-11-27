package de.jet.paper.mutable

import de.jet.jvm.tool.mutable.FlexibleMutable
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache

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
