package de.jet.paper.app

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.runtime.sandbox.SandBox
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.AppCache
import de.jet.paper.structure.app.cache.CacheDepthLevel
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.component.Component
import de.jet.paper.structure.feature.Feature
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.data.Preference
import de.jet.paper.tool.display.item.action.ItemClickAction
import de.jet.paper.tool.display.item.action.ItemInteractAction
import de.jet.paper.tool.display.ui.panel.PanelFlag
import de.jet.paper.tool.input.Keyboard
import de.jet.paper.tool.input.Keyboard.RunningEngine.PlayerKeyboardPort
import de.jet.paper.tool.position.LocationBox
import de.jet.paper.tool.timing.cooldown.Cooldown
import de.jet.paper.tool.timing.tasky.Tasky
import org.bukkit.OfflinePlayer
import org.bukkit.entity.HumanEntity
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

	val registeredPreferences = mutableMapOf<Identity<out Preference<*>>, Preference<*>>()

	val livingCooldowns = mutableMapOf<String, Cooldown>()

	var runningServiceTaskController = mutableMapOf<Identity<Service>, Tasky>()
		internal set

	val runningTasks = mutableListOf<Int>()

	val runningKeyboards = mutableMapOf<PlayerKeyboardPort, String>()

	val buildModePlayers = mutableSetOf<Identity<out OfflinePlayer>>()

	val playerMarkerBoxes = mutableMapOf<Identity<out OfflinePlayer>, LocationBox>()

	val featureStates = mutableMapOf<Identity<Feature>, Feature.FeatureState>()

	val keyboardRequests = mutableListOf<Keyboard.KeyboardRequest<out HumanEntity>>()

	internal val tmp_initSetupPreferences = mutableSetOf<Preference<*>>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		// TODO("Not yet implemented")
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		// TODO("Not yet implemented")
	}
}