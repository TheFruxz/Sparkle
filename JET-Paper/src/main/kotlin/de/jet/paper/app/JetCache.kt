package de.jet.paper.app

import de.jet.jvm.extension.collection.removeAll
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.extension.debugLog
import de.jet.paper.runtime.sandbox.SandBox
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.AppCache
import de.jet.paper.structure.app.cache.CacheDepthLevel
import de.jet.paper.structure.app.cache.CacheDepthLevel.*
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
import kotlin.reflect.full.findAnnotations

object JetCache : AppCache {

	@GlobalData
	@DataLevel(KILL)
	val registeredApplications = mutableSetOf<App>()

	@GlobalData
	@DataLevel(KILL)
	val registeredSandBoxes = mutableSetOf<SandBox>()

	@GlobalData
	@DataLevel(KILL)
	val registeredSandBoxCalls = mutableMapOf<Identity<SandBox>, Int>()

	@GlobalData
	@DataLevel(KILL)
	val registeredCompletionVariables = mutableMapOf<String, Set<String>>()

	@GlobalData
	@DataLevel(KILL)
	val registeredCachedMutables = mutableMapOf<String, Any?>()

	@GlobalData
	@DataLevel(DUMP)
	val registeredPreferenceCache = mutableMapOf<String, Any>()

	@GlobalData
	@DataLevel(KILL)
	val registeredItemClickActions = mutableMapOf<String, ItemClickAction>()

	@GlobalData
	@DataLevel(KILL)
	val registeredItemInteractActions = mutableMapOf<String, ItemInteractAction>()

	@GlobalData
	@DataLevel(KILL)
	val registeredInterchanges = mutableSetOf<Interchange>()

	@GlobalData
	@DataLevel(KILL)
	val registeredComponents = mutableSetOf<Component>()

	@GlobalData
	@DataLevel(KILL)
	val registeredServices = mutableSetOf<Service>()

	@GlobalData
	@DataLevel(KILL)
	val runningComponents = mutableSetOf<Identity<Component>>()

	@GlobalData
	@DataLevel(KILL)
	val registeredPanelFlags = mutableMapOf<String, Set<PanelFlag>>()

	@GlobalData
	@DataLevel(KILL)
	val registeredPreferences = mutableMapOf<Identity<out Preference<*>>, Preference<*>>()

	@GlobalData
	@DataLevel(CLEAR)
	val livingCooldowns = mutableMapOf<String, Cooldown>()

	@GlobalData
	@DataLevel(KILL)
	var runningServiceTaskController = mutableMapOf<Identity<Service>, Tasky>()
		internal set

	@GlobalData
	@DataLevel(KILL)
	val runningTasks = mutableListOf<Int>()

	@EntityData
	@DataLevel(CLEAR)
	val runningKeyboards = mutableMapOf<PlayerKeyboardPort, String>()

	@EntityData
	@DataLevel(CLEAR)
	val buildModePlayers = mutableSetOf<Identity<out OfflinePlayer>>()

	@EntityData
	@DataLevel(CLEAR)
	val playerMarkerBoxes = mutableMapOf<Identity<out OfflinePlayer>, LocationBox>()

	@GlobalData
	@DataLevel(KILL)
	val featureStates = mutableMapOf<Identity<Feature>, Feature.FeatureState>()

	@EntityData
	@DataLevel(CLEAR)
	val keyboardRequests = mutableListOf<Keyboard.KeyboardRequest<out HumanEntity>>()

	@GlobalData
	@DataLevel(KILL)
	internal val tmp_initSetupPreferences = mutableSetOf<Preference<*>>()

	private fun entityCleanerObjects(entity: UUID) = mapOf(
		this::runningKeyboards to { runningKeyboards.removeAll { key, _ -> key.player == entity } },
		this::buildModePlayers to { buildModePlayers.removeAll { it.identity == "" + entity } },
		this::playerMarkerBoxes to { playerMarkerBoxes.removeAll { key, _ -> key.identity == "" + entity } },
		this::keyboardRequests to { keyboardRequests.removeAll { it.holder.uniqueId == entity } },
	)

	private val cleanerObjects = mutableListOf(
		this::registeredApplications to { registeredApplications.clear() },
		this::registeredSandBoxes to { registeredSandBoxes.clear() },
		this::registeredSandBoxCalls to { registeredSandBoxCalls.clear() },
		this::registeredCompletionVariables to { registeredCompletionVariables.clear() },
		this::registeredCachedMutables to { registeredCachedMutables.clear() },
		this::registeredPreferenceCache to { registeredPreferenceCache.clear() },
		this::registeredItemClickActions to { registeredItemClickActions.clear() },
		this::registeredItemInteractActions to { registeredItemInteractActions.clear() },
		this::registeredInterchanges to { registeredInterchanges.clear() },
		this::registeredComponents to { registeredComponents.clear() },
		this::registeredServices to { registeredServices.clear() },
		this::runningComponents to { runningComponents.clear() },
		this::registeredPanelFlags to { registeredPanelFlags.clear() },
		this::registeredPreferences to { registeredPreferences.clear() },
		this::livingCooldowns to { livingCooldowns.clear() },
		this::runningServiceTaskController to { runningServiceTaskController.clear() },
		this::runningTasks to { runningTasks.clear() },
		this::runningKeyboards to { runningKeyboards.clear() },
		this::buildModePlayers to { buildModePlayers.clear() },
		this::playerMarkerBoxes to { playerMarkerBoxes.clear() },
		this::featureStates to { featureStates.clear() },
		this::keyboardRequests to { keyboardRequests.clear() }
	)

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		@OptIn(ExperimentalStdlibApi::class)

		entityCleanerObjects(entityIdentity).forEach { (obj, cleaner) ->
			if (obj.findAnnotations(DataLevel::class).any { dropDepth.isDeeperThanOrEquals(it.level) }) {
				cleaner()
				debugLog("Dropped '${obj.name}': ${obj.findAnnotations(DataLevel::class).first().level} cache by query 'drop -e $entityIdentity at $dropDepth'")
			}
		}
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {
		@OptIn(ExperimentalStdlibApi::class)
		@Suppress("GrazieInspection")

		cleanerObjects.forEach { (obj, cleaner) ->
			if (obj.findAnnotations(DataLevel::class).any { dropDepth.isDeeperThanOrEquals(it.level) }) {
				cleaner()
				debugLog("Dropped '${obj.name}': ${obj.findAnnotations(DataLevel::class).first().level} cache by query 'drop -a at $dropDepth'")
			}
		}

	}

	private annotation class EntityData
	private annotation class GlobalData
	private annotation class DataLevel(val level: CacheDepthLevel)

}