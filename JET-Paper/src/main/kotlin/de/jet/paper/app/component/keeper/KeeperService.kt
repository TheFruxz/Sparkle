package de.jet.paper.app.component.keeper

import de.jet.jvm.extension.catchException
import de.jet.jvm.extension.collection.withForEach
import de.jet.jvm.extension.display.display
import de.jet.paper.app.JetApp
import de.jet.paper.app.JetCache
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.system
import de.jet.paper.extension.timing.isOver
import de.jet.paper.structure.app.cache.CacheDepthLevel.DUMP
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.timing.tasky.Tasky
import de.jet.paper.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level

internal class KeeperService : Service {

	override val vendor = system

	override val thisIdentity = "iKeeper"

	override val temporalAdvice = TemporalAdvice.ticking(20, 20L*60*5, false)

	override val onStart: Tasky.() -> Unit = {
		sectionLog.log(Level.INFO, "Hey! I'm starting to clean your JET!")
	}

	override val process: Tasky.() -> Unit = {

		// Cleaning Cooldowns

		JetCache.livingCooldowns.toList().forEach { (key, value) ->

			if (value.isOver) {
				JetCache.livingCooldowns.remove(key)
				debugLog("removed livingCooldown $key, because, it was over; remaining: ${value.remainingTime.display()}")
			}

		}

		JetCache.registeredApplications.withForEach {
			try {
				val level = DUMP
				appCache.dropEverything(level)
				debugLog("removed appCache (level: ${level.name}) from app $appLabel")
			} catch (exception: Exception) {

				if (JetApp.debugMode)
					catchException(exception)

				debugLog("failed to clean appCache of '$appLabel'${ if (JetApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}