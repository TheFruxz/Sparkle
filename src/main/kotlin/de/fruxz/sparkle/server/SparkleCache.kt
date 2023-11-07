package de.fruxz.sparkle.server

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
import dev.fruxz.ascend.tool.smart.identity.RelatedIdentity
import dev.fruxz.ascend.tool.time.calendar.Calendar
import dev.fruxz.ascend.tool.time.cooldown.StaticCooldown
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

	var registeredSandBoxCalls = mapOf<RelatedIdentity<SandBox, Key>, Int>()

	var registeredCompletionAssetStateCache = mapOf<String, SortedSet<String>>()

	var registeredPreferenceCache = mapOf<PreferenceIndex<*>, Any>()

	var registeredInterchanges = setOf<Interchange>()

	var disabledInterchanges = setOf<RelatedIdentity<out Interchange, String>>()

	var registeredComponents = setOf<Component>()

	var registeredListeners = setOf<EventListener>()

	var runningComponents = mapOf<RelatedIdentity<out Component, Key>, Calendar>()

	var registeredPreferences = mapOf<RelatedIdentity<out Preference<*>, String>, Preference<*>>()

	var playerMarkerBoxes = mapOf<RelatedIdentity<out OfflinePlayer, UUID>, DependentCubicalShape>()

	var tmp_initSetupPreferences = setOf<Preference<*>>()

	var itemActions = setOf<ItemAction<out Event>>()

	var canvasSessions = mapOf<Player, CanvasSession>()

	var services = mapOf<Key, CoroutineScope>()

	var serviceStates = mapOf<Key, ServiceState>()

	var updateProcesses = mapOf<App, Job>()

	var updateStates = mapOf<App, UpdateCheckResult>()

	override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) {
		when {
			dropDepth.isDeeperThanOrEquals(CLEAR) -> {
				playerMarkerBoxes = playerMarkerBoxes.filter { it.key.value != entityIdentity }
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
			registeredInterchanges = emptySet()
			registeredComponents = emptySet()
			services = emptyMap()
			serviceStates = emptyMap()
			registeredListeners = emptySet()
			runningComponents = emptyMap()
			registeredPreferences = emptyMap()
			tmp_initSetupPreferences = emptySet()
			disabledInterchanges = emptySet()
		}

	}

}