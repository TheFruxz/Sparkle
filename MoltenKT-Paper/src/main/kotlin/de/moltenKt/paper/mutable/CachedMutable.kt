package de.moltenKt.paper.mutable

import de.moltenKt.core.tool.mutable.FlexibleMutable
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache

class CachedMutable<T>(
	val cacheId: String,
	val default: T
) : FlexibleMutable<T>(default), Identifiable<CachedMutable<T>> {

	init {

		// If not something is already cached, place default
		if (!MoltenCache.registeredCachedMutables.contains(cacheId)) {
			MoltenCache.registeredCachedMutables[cacheId] = property
		}

	}

	@Suppress("UNCHECKED_CAST")
	override var property: T
		get() = MoltenCache.registeredCachedMutables[cacheId] as T ?: default
		set(value) {
			MoltenCache.registeredCachedMutables[cacheId] = value
		}

	override val identity = cacheId

}
