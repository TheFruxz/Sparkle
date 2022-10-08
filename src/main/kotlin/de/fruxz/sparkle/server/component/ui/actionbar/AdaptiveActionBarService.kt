package de.fruxz.sparkle.server.component.ui.actionbar

import de.fruxz.sparkle.framework.extension.onlinePlayers
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceActions
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceTimes
import de.fruxz.sparkle.framework.infrastructure.service.ServiceIteration
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent.Companion.globalLayers
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent.Companion.playerLayers
import kotlin.time.Duration.Companion.seconds

internal class AdaptiveActionBarService(override val vendor: App = system) : Service {

	override val label = "AdaptiveActionBar"

	override val serviceTimes = ServiceTimes(.5.seconds, .2.seconds, true)
	override val serviceActions = ServiceActions()

	override val iteration: ServiceIteration = {

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