package de.jet.app

import de.jet.library.runtime.sandbox.SandBox
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCache
import de.jet.library.structure.app.cache.CacheDepthLevel
import de.jet.library.structure.component.Component
import de.jet.library.tool.display.item.action.ItemClickAction
import de.jet.library.tool.display.item.action.ItemInteractAction
import de.jet.library.tool.display.ui.panel.PanelFlag
import java.util.*

object JetCache : AppCache {

	val registeredApplications = mutableSetOf<App>()

	val registeredSandBoxes = mutableMapOf<String, SandBox>()

	val registeredCompletionVariables = mutableMapOf<String, Set<String>>()

	val registeredCachedMutables = mutableMapOf<String, Any?>()

	val registeredPreferenceCache = mutableMapOf<String, Any>()

	val registeredItemClickActions = mutableMapOf<String, ItemClickAction>()

	val registeredItemInteractActions = mutableMapOf<String, ItemInteractAction>()

	val registeredComponents = mutableSetOf<Component>()

	val registeredPanelFlags = mutableMapOf<String, Set<PanelFlag>>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}
}