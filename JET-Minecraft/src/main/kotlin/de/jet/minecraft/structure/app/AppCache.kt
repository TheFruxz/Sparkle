package de.jet.minecraft.structure.app

import de.jet.minecraft.structure.app.cache.CacheDepthLevel
import java.util.*

interface AppCache {

	fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel)

	fun dropEverything(dropDepth: CacheDepthLevel)

}