package de.fruxz.sparkle.server

import de.fruxz.ascend.extension.classType.UUID
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.ascend.tool.timing.cooldown.StaticCooldown
import de.fruxz.sparkle.framework.util.sandbox.SandBox
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.AppCache
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel.*
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.infrastructure.feature.Feature
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.util.data.Preference
import de.fruxz.sparkle.framework.util.data.Preference.PreferenceIndex
import de.fruxz.sparkle.framework.util.extension.debugLog
import de.fruxz.sparkle.framework.util.visual.item.action.ItemAction
import de.fruxz.sparkle.framework.util.positioning.dependent.DependentCubicalShape
import de.fruxz.sparkle.framework.util.scheduler.Tasky
import de.fruxz.sparkle.framework.util.visual.canvas.CanvasSessionManager.CanvasSession
import net.kyori.adventure.key.Key
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event

object SparkleCache : AppCache {

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

	var itemActions = setOf<ItemAction<out Event>>()

	var messageConversationPartners = mapOf<Player, Player>()

	var canvasSessions = mapOf<Player, CanvasSession>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		when {
			dropDepth.isDeeperThanOrEquals(CLEAR) -> {
				buildModePlayers = buildModePlayers.filter { it.identity != "" + entityIdentity }.toSet()
				playerMarkerBoxes = playerMarkerBoxes.filter { it.key.identity != "" + entityIdentity }
				messageConversationPartners = messageConversationPartners.filter { it.value.uniqueId != entityIdentity && it.key.uniqueId != entityIdentity }
			}
		}
	}

	override fun dropEverything(dropDepth: CacheDepthLevel) {

		registeredPreferenceCache = registeredPreferenceCache.filterNot { dropDepth.isDeeperThanOrEquals(it.key.cacheDepthLevel) }

		if (dropDepth.isDeeperThanOrEquals(DUMP)) {
			debugLog("Cache clear 'DUMP' reached")
			registeredCompletionAssetStateCache = emptyMap()
			StaticCooldown.clean()
		}

		if (dropDepth.isDeeperThanOrEquals(CLEAN)) {
			debugLog("Cache clear 'CLEAN' reached")

		}

		if (dropDepth.isDeeperThanOrEquals(CLEAR)) {
			debugLog("Cache clear 'CLEAR' reached")
			itemActions = emptySet()
			canvasSessions = emptyMap()
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
		}

	}

}