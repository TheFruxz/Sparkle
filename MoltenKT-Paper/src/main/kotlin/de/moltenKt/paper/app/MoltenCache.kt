package de.moltenKt.paper.app

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.core.tool.timing.calendar.Calendar
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
import de.moltenKt.paper.tool.display.canvas.Canvas
import de.moltenKt.paper.tool.display.canvas.CanvasSessionManager.CanvasSession
import de.moltenKt.paper.tool.display.item.action.ItemAction
import de.moltenKt.paper.tool.position.CubicalShape
import de.moltenKt.paper.tool.timing.cooldown.Cooldown
import de.moltenKt.paper.tool.timing.tasky.Tasky
import net.kyori.adventure.key.Key
import org.bukkit.OfflinePlayer
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event

object MoltenCache : AppCache {

	@GlobalData
	@DataLevel(KILL)
	var registeredApps = setOf<App>()

	@GlobalData
	@DataLevel(KILL)
	var registeredSandBoxes = setOf<SandBox>()

	@GlobalData
	@DataLevel(KILL)
	var registeredSandBoxCalls = mapOf<Identity<SandBox>, Int>()

	@GlobalData
	@DataLevel(DUMP)
	var registeredCompletionAssetStateCache = mapOf<String, Set<String>>()

	@GlobalData
	@DataLevel(KILL)
	var registeredCachedMutables = mapOf<String, Any?>()

	@GlobalData
	@DataLevel(DUMP)
	var registeredPreferenceCache = mapOf<String, Any>()

	@GlobalData
	@DataLevel(KILL)
	var registeredInterchanges = setOf<Interchange>()

	@GlobalData
	@DataLevel(KILL)
	var registeredComponents = setOf<Component>()

	@GlobalData
	@DataLevel(KILL)
	var registeredServices = setOf<Service>()

	@GlobalData
	@DataLevel(KILL)
	var registeredListeners = setOf<EventListener>()

	@GlobalData
	@DataLevel(KILL)
	var runningComponents = mapOf<Identity<out Component>, Calendar>()

	@GlobalData
	@DataLevel(KILL)
	var registeredPreferences = mapOf<Identity<out Preference<*>>, Preference<*>>()

	@GlobalData
	@DataLevel(CLEAR)
	var livingCooldowns = mapOf<String, Cooldown>()

	@GlobalData
	@DataLevel(KILL)
	var runningServiceTaskController = mapOf<Identity<Service>, Tasky>()

	@GlobalData
	@DataLevel(KILL)
	var runningTasks = listOf<Int>()

	@EntityData
	@DataLevel(CLEAR)
	var buildModePlayers = setOf<Identity<out OfflinePlayer>>()

	@EntityData
	@DataLevel(CLEAR)
	var playerMarkerBoxes = mapOf<Identity<out OfflinePlayer>, CubicalShape>()

	@GlobalData
	@DataLevel(KILL)
	var featureStates = mutableMapOf<Identity<Feature>, Feature.FeatureState>()

	@GlobalData
	@DataLevel(KILL)
	var tmp_initSetupPreferences = setOf<Preference<*>>()

	@GlobalData
	@DataLevel(KILL)
	var initializationProcesses = setOf<() -> Unit>()

	@GlobalData
	@DataLevel(CLEAR)
	var itemActions = setOf<ItemAction<out Event>>()

	@EntityData
	@DataLevel(CLEAR)
	var messageConversationPartners = mapOf<Player, Player>()

	@EntityData
	@DataLevel(CLEAR)
	var canvasActions = mapOf<Key, Canvas.Reaction>()

	@EntityData
	@DataLevel(CLEAR)
	var canvasSessions = mapOf<HumanEntity, CanvasSession>()

	@GlobalData
	@DataLevel(CLEAR)
	var canvas = mapOf<Key, Canvas>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {

	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {

	}

	private annotation class EntityData
	private annotation class GlobalData
	private annotation class DataLevel(val level: CacheDepthLevel)

}