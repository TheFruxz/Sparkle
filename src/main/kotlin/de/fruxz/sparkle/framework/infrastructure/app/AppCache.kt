package de.fruxz.sparkle.framework.infrastructure.app

import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import java.util.*

interface AppCache {

	fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel)

	fun dropEverything(dropDepth: CacheDepthLevel)

}