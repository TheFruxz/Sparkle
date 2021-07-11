package io.quad.library.structure.app

import io.quad.library.structure.app.cache.CacheDepthLevel
import java.util.*

interface AppCache {

	fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel)

	fun dropEverything(dropDepth: CacheDepthLevel)

}