package de.fruxz.sparkle.structure.app

import de.fruxz.sparkle.structure.app.cache.CacheDepthLevel
import java.util.*

interface AppCache {

	fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel)

	fun dropEverything(dropDepth: CacheDepthLevel)

}