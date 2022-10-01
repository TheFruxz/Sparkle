package de.fruxz.sparkle.app.component.ui.actionbar

import de.fruxz.sparkle.app.component.ui.actionbar.AdaptiveActionBarComponent.Companion.globalLayers
import de.fruxz.sparkle.app.component.ui.actionbar.AdaptiveActionBarComponent.Companion.playerLayers
import de.fruxz.sparkle.extension.paper.onlinePlayers
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.structure.service.Service
import de.fruxz.sparkle.tool.timing.tasky.Tasky
import de.fruxz.sparkle.tool.timing.tasky.TemporalAdvice
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