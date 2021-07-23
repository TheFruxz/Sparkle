package de.jet.app

import de.jet.library.runtime.sandbox.SandBox
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCache
import de.jet.library.structure.app.cache.CacheDepthLevel
import java.util.*

object JetCache : AppCache {

	val registeredApplications = mutableSetOf<App>()

	val registeredSandBoxes = mutableMapOf<String, SandBox>()

	val registeredCompletionVariables = mutableMapOf<String, Set<String>>()

	val registeredCachedMutables = mutableMapOf<String, Any?>()

	val registeredPreferenceCache = mutableMapOf<String, Any>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}
}