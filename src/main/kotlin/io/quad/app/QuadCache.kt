package io.quad.app

import io.quad.library.runtime.sandbox.SandBox
import io.quad.library.structure.app.App
import io.quad.library.structure.app.AppCache
import io.quad.library.structure.app.cache.CacheDepthLevel
import java.util.*

object QuadCache : AppCache {

	val registeredApplications = mutableSetOf<App>()

	val registeredSandBoxes = mutableMapOf<String, SandBox>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}
}