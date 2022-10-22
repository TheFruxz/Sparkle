package de.fruxz.sparkle.server

import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.ascend.tool.timing.cooldown.StaticCooldown
import de.fruxz.sparkle.framework.data.Preference
import de.fruxz.sparkle.framework.data.Preference.PreferenceIndex
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.AppCache
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel.*
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateCheckResult
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceState
import de.fruxz.sparkle.framework.positioning.dependent.DependentCubicalShape
import de.fruxz.sparkle.framework.sandbox.SandBox
import de.fruxz.sparkle.framework.visual.canvas.session.CanvasSessionManager.CanvasSession
import de.fruxz.sparkle.framework.visual.item.action.ItemAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.kyori.adventure.key.Key
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.util.*

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

	var registeredListeners = setOf<EventListener>()

	var runningComponents = mapOf<Identity<out Component>, Calendar>()

	var registeredPreferences = mapOf<Identity<out Preference<*>>, Preference<*>>()

	var runningTasks = listOf<Int>()

	var buildModePlayers = setOf<Identity<out OfflinePlayer>>()

	var playerMarkerBoxes = mapOf<Identity<out OfflinePlayer>, DependentCubicalShape>()

	var tmp_initSetupPreferences = setOf<Preference<*>>()

	var itemActions = setOf<ItemAction<out Event>>()

	var messageConversationPartners = mapOf<Player, Player>()

	var canvasSessions = mapOf<Player, CanvasSession>()

	var services = mapOf<Key, CoroutineScope>()

	var serviceStates = mapOf<Key, ServiceState>()

	var updateProcesses = mapOf<App, Job>()

	var updateStates = mapOf<App, UpdateCheckResult>()

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
			updateStates = emptyMap()
		}

		if (dropDepth.isDeeperThanOrEquals(CLEAR)) {
			debugLog("Cache clear 'CLEAR' reached")
			itemActions = emptySet()
			canvasSessions = emptyMap()
			updateProcesses = emptyMap()
		}

		if (dropDepth.isDeeperThanOrEquals(KILL)) {
			debugLog("Cache clear 'KILL' reached")
			registeredApps = emptySet()
			registeredSandBoxes = emptySet()
			registeredSandBoxCalls = emptyMap()
			registeredCachedMutables = emptyMap()
			registeredInterchanges = emptySet()
			registeredComponents = emptySet()
			services = emptyMap()
			serviceStates = emptyMap()
			registeredListeners = emptySet()
			runningComponents = emptyMap()
			registeredPreferences = emptyMap()
			runningTasks = emptyList()
			tmp_initSetupPreferences = emptySet()
			disabledInterchanges = emptySet()
		}

	}

}