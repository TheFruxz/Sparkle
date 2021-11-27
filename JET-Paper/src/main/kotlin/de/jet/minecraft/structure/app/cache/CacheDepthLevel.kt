package de.jet.minecraft.structure.app.cache

enum class CacheDepthLevel {

	DUMP, CLEAN, CLEAR, KILL;

	fun isDeeperThan(cacheDepthLevel: CacheDepthLevel) = cacheDepthLevel.ordinal > ordinal

	fun isDeeperThanOrEquals(cacheDepthLevel: CacheDepthLevel) = cacheDepthLevel.ordinal >= ordinal

}
