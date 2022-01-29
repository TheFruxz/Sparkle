package de.jet.paper.structure.app.cache

enum class CacheDepthLevel {

	/**
	 * Regular cleaning level, no game breaking changes.
	 */
	DUMP,

	/**
	 * Hard cleaning level, some game behavior changes, but only affecting background operations.
	 */
	CLEAN,

	/**
	 * Hard clear level, some game elements reset and some stuff is deleted, that can change visual look.
	 */
	CLEAR,

	/**
	 * Kill level, everything is deleted, that can break the games current running state.
	 */
	KILL;

	fun isDeeperThan(cacheDepthLevel: CacheDepthLevel) = cacheDepthLevel < this

	fun isDeeperThanOrEquals(cacheDepthLevel: CacheDepthLevel) = cacheDepthLevel <= this

}
