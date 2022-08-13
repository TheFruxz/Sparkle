package de.moltenKt.paper.app.component.ui.actionbar

import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent.Companion.globalLayers
import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent.Companion.playerLayers
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
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