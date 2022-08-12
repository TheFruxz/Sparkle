package de.moltenKt.paper.app

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.extension.debugLog
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
import de.moltenKt.paper.tool.data.Preference.PreferenceIndex
import de.moltenKt.paper.tool.display.canvas.Canvas
import de.moltenKt.paper.tool.display.canvas.CanvasSessionManager.CanvasSession
import de.moltenKt.paper.tool.display.item.action.ItemAction
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import de.moltenKt.paper.tool.timing.tasky.Tasky
import net.kyori.adventure.key.Key
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event

object MoltenCache : AppCache {

	var registeredApps = setOf<App>()

	var registeredSandBoxes = setOf<SandBox>()

	var registeredSandBoxCalls = mapOf<Identity<SandBox>, Int>()

	var registeredCompletionAssetStateCache = mapOf<String, Set<String>>()

	var registeredCachedMutables = mapOf<String, Any?>()

	var registeredPreferenceCache = mapOf<PreferenceIndex<*>, Any>()

	var registeredInterchanges = setOf<Interchange>()

	var disabledInterchanges = setOf<Identity<out Interchange>>()

	var registeredComponents = setOf<Component>()

	var registeredServices = setOf<Service>()

	var registeredListeners = setOf<EventListener>()

	var runningComponents = mapOf<Identity<out Component>, Calendar>()

	var registeredPreferences = mapOf<Identity<out Preference<*>>, Preference<*>>()

	var runningServiceTaskController = mapOf<Key, Tasky>()

	var runningTasks = listOf<Int>()

	var buildModePlayers = setOf<Identity<out OfflinePlayer>>()

	var playerMarkerBoxes = mapOf<Identity<out OfflinePlayer>, DependentCubicalShape>()

	var featureStates = mapOf<Identity<Feature>, Feature.FeatureState>()

	var tmp_initSetupPreferences = setOf<Preference<*>>()

	var initializationProcesses = setOf<suspend () -> Unit>()

	var itemActions = setOf<ItemAction<out Event>>()

	var messageConversationPartners = mapOf<Player, Player>()

	var canvasActions = mapOf<Key, Canvas.Reaction>()

	var canvasSessions = mapOf<Player, CanvasSession>()

	var canvas = mapOf<Key, Canvas>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		when {
			dropDepth.isDeeperThanOrEquals(CLEAR) -> {
				buildModePlayers = buildModePlayers.filter { it.identity != "" + entityIdentity }.toSet()
				playerMarkerBoxes = playerMarkerBoxes.filter { it.key.identity != "" + entityIdentity }
				messageConversationPartners = messageConversationPartners.filter { it.value.uniqueId != entityIdentity && it.key.uniqueId != entityIdentity }
				canvasSessions = canvasSessions.filter { it.key.uniqueId != entityIdentity }
			}
		}
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {

		registeredPreferenceCache = registeredPreferenceCache.filterNot { dropDepth.isDeeperThanOrEquals(it.key.cacheDepthLevel) }

		if (dropDepth.isDeeperThanOrEquals(DUMP)) {
			debugLog("Cache clear 'DUMP' reached")
			registeredCompletionAssetStateCache = emptyMap()
		}

		if (dropDepth.isDeeperThanOrEquals(CLEAN)) {
			debugLog("Cache clear 'CLEAN' reached")

		}

		if (dropDepth.isDeeperThanOrEquals(CLEAR)) {
			debugLog("Cache clear 'CLEAR' reached")
			itemActions = emptySet()
			canvasActions = emptyMap()
			canvas = emptyMap()
		}

		if (dropDepth.isDeeperThanOrEquals(KILL)) {
			debugLog("Cache clear 'KILL' reached")
			registeredApps = emptySet()
			registeredSandBoxes = emptySet()
			registeredSandBoxCalls = emptyMap()
			registeredCachedMutables = emptyMap()
			registeredInterchanges = emptySet()
			registeredComponents = emptySet()
			registeredServices = emptySet()
			registeredListeners = emptySet()
			runningComponents = emptyMap()
			registeredPreferences = emptyMap()
			runningServiceTaskController = emptyMap()
			runningTasks = emptyList()
			featureStates = emptyMap()
			tmp_initSetupPreferences = emptySet()
			initializationProcesses = emptySet()
		}

	}

}