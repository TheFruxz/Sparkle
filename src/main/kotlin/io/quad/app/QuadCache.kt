package io.quad.app

import io.quad.library.structure.app.App
import io.quad.library.structure.app.AppCache
import io.quad.library.structure.app.cache.CacheDepthLevel
import java.util.*

object QuadCache : AppCache {

	val registeredApplications = mutableSetOf<App>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}
}