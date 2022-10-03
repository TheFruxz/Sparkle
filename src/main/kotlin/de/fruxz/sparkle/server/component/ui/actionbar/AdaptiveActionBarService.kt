package de.fruxz.sparkle.server.component.ui.actionbar

import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent.Companion.globalLayers
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent.Companion.playerLayers
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.util.extension.onlinePlayers
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.scheduler.Tasky
import de.fruxz.sparkle.framework.util.scheduler.TemporalAdvice
import kotlin.time.Duration.Companion.seconds

internal class AdaptiveActionBarService : Service {

	override val label = "AdaptiveActionBar"

	override val vendor = system

	override val temporalAdvice = TemporalAdvice.ticking(.5.seconds, .2.seconds, true)

	override val process: Tasky.() -> Unit = {
		val global = globalLayers

		if (global.isNotEmpty() || playerLayers.isNotEmpty()) {

			onlinePlayers.forEach { target ->
				val specific = playerLayers[target]

				if (!specific.isNullOrEmpty()) {
					specific.withIndex().forEach { (index, content) ->
						if (index == specific.indexOfLast { it.displayCondition.invoke(target) }) target.sendActionBar(content.content.invoke(target))
						if (!content.expiration.invoke(target).inFuture) playerLayers += target to (specific - content)
					}
				} else {
					if (global.isNotEmpty()) {
						global.withIndex().forEach { (index, content) ->
							if (index == global.indexOfLast { it.displayCondition.invoke(target) }) target.sendActionBar(content.content.invoke(target))
							if (!content.expiration.invoke(target).inFuture) globalLayers -= content
						}
					}
				}

			}

		}

	}

}