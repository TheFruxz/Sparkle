package de.moltenKt.paper.app

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.core.extension.container.removeAll
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.runtime.event.PanelClickEvent
import de.moltenKt.paper.runtime.sandbox.SandBox
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.AppCache
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel.*
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.structure.feature.Feature
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.data.Preference
import de.moltenKt.paper.tool.display.item.action.ItemAction
import de.moltenKt.paper.tool.display.item.action.ItemClickAction
import de.moltenKt.paper.tool.display.item.action.ItemInteractAction
import de.moltenKt.paper.tool.display.ui.panel.Panel
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import de.moltenKt.paper.tool.position.CubicalShape
import de.moltenKt.paper.tool.timing.cooldown.Cooldown
import de.moltenKt.paper.tool.timing.tasky.Tasky
import kotlinx.coroutines.Job
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import kotlin.reflect.full.findAnnotations

object MoltenCache : AppCache {

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
	@DataLevel(DUMP)
	val registeredCompletionAssetStateCache = mutableMapOf<String, Set<String>>()

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
	val registeredListeners = mutableSetOf<EventListener>()

	@GlobalData
	@DataLevel(KILL)
	val runningComponents = mutableMapOf<Identity<Component>, Calendar>()

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
	val buildModePlayers = mutableSetOf<Identity<out OfflinePlayer>>()

	@EntityData
	@DataLevel(CLEAR)
	val playerMarkerBoxes = mutableMapOf<Identity<out OfflinePlayer>, CubicalShape>()

	@GlobalData
	@DataLevel(KILL)
	val featureStates = mutableMapOf<Identity<Feature>, Feature.FeatureState>()

	@GlobalData
	@DataLevel(KILL)
	internal val tmp_initSetupPreferences = mutableSetOf<Preference<*>>()

	@GlobalData
	@DataLevel(KILL)
	internal val initializationProcesses = mutableListOf<() -> Unit>()

	@GlobalData
	@DataLevel(CLEAR)
	internal val completedPanels = mutableSetOf<Panel>()

	@GlobalData
	@DataLevel(CLEAR)
	internal val panelInteractions = mutableMapOf<Identity<out Panel>, MutableList<PanelClickEvent.() -> Unit>>()

	@GlobalData
	@DataLevel(CLEAR)
	internal val itemActions = mutableListOf<ItemAction<out Event>>()

	@EntityData
	@DataLevel(CLEAR)
	internal val splashScreens = mutableMapOf<Player, Job>()

	private fun entityCleanerObjects(entity: UUID) = mapOf(
		this::buildModePlayers to { buildModePlayers.removeAll { it.identity == "" + entity } },
		this::playerMarkerBoxes to { playerMarkerBoxes.removeAll { key, _ -> key.identity == "" + entity } },
	)

	private val cleanerObjects = mutableListOf(
		this::registeredApplications to { registeredApplications.clear() },
		this::registeredSandBoxes to { registeredSandBoxes.clear() },
		this::registeredSandBoxCalls to { registeredSandBoxCalls.clear() },
		this::registeredCompletionAssetStateCache to { registeredCompletionAssetStateCache.clear() },
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
		this::buildModePlayers to { buildModePlayers.clear() },
		this::playerMarkerBoxes to { playerMarkerBoxes.clear() },
		this::featureStates to { featureStates.clear() },
		this::tmp_initSetupPreferences to { tmp_initSetupPreferences.clear() },
		this::initializationProcesses to { initializationProcesses.clear() },
		this::panelInteractions to { panelInteractions.clear() },
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