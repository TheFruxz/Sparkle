package de.jet.app

import de.jet.library.runtime.sandbox.SandBox
import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCache
import de.jet.library.structure.app.cache.CacheDepthLevel
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.component.Component
import de.jet.library.structure.service.Service
import de.jet.library.tool.data.Preference
import de.jet.library.tool.display.item.action.ItemClickAction
import de.jet.library.tool.display.item.action.ItemInteractAction
import de.jet.library.tool.display.ui.panel.PanelFlag
import de.jet.library.tool.smart.Identity
import de.jet.library.tool.timing.cooldown.Cooldown
import de.jet.library.tool.timing.tasky.Tasky
import org.bukkit.OfflinePlayer
import java.util.*

object JetCache : AppCache {

	val registeredApplications = mutableSetOf<App>()

	val registeredSandBoxes = mutableSetOf<SandBox>()

	val registeredSandBoxCalls = mutableMapOf<Identity<SandBox>, Int>()

	val registeredCompletionVariables = mutableMapOf<String, Set<String>>()

	val registeredCachedMutables = mutableMapOf<String, Any?>()

	val registeredPreferenceCache = mutableMapOf<String, Any>()

	val registeredItemClickActions = mutableMapOf<String, ItemClickAction>()

	val registeredItemInteractActions = mutableMapOf<String, ItemInteractAction>()

	val registeredInterchanges = mutableSetOf<Interchange>()

	val registeredComponents = mutableSetOf<Component>()

	val registeredServices = mutableSetOf<Service>()

	val runningComponents = mutableSetOf<Identity<Component>>()

	val registeredPanelFlags = mutableMapOf<String, Set<PanelFlag>>()

	val livingCooldowns = mutableMapOf<String, Cooldown>()

	var runningServiceTaskController = mutableMapOf<Identity<Service>, Tasky>()
		internal set

	val runningTasks = mutableListOf<Int>()

	val buildModePlayers = mutableSetOf<Identity<out OfflinePlayer>>()

	internal val tmp_initSetupPreferences = mutableSetOf<Preference<*>>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		TODO("Not yet implemented")
	}
}