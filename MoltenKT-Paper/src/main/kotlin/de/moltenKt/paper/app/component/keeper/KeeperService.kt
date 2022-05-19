package de.moltenKt.paper.app.component.keeper

import de.moltenKt.core.extension.catchException
import de.moltenKt.core.extension.container.withForEach
import de.moltenKt.core.extension.display.display
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.extension.timing.isOver
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel.DUMP
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

internal class KeeperService : Service {

	override val vendor = system

	override val thisIdentity = "iKeeper"

	override val temporalAdvice = TemporalAdvice.ticking(1.seconds, 20.minutes, false)

	override val onStart: Tasky.() -> Unit = {
		sectionLog.log(Level.INFO, "Hey! I'm starting to clean your App-Caches!")
	}

	override val process: Tasky.() -> Unit = {

		// Cleaning the cooldowns, that got stuck in the cache
		MoltenCache.livingCooldowns.toList().forEach { (key, value) ->

			if (value.isOver) {
				MoltenCache.livingCooldowns -= key
				debugLog("removed livingCooldown $key, because, it was over; remaining: ${value.remainingTime.display()}")
			}

		}

		// Cleaning the individual caches of the different registered apps
		MoltenCache.registeredApps.withForEach {
			try {
				val level = DUMP
				appCache.dropEverything(level)
				debugLog("removed appCache (level: ${level.name}) from app $appLabel")
			} catch (exception: Exception) {

				if (MoltenApp.debugMode) catchException(exception)

				debugLog("failed to clean appCache of '$appLabel'${ if (MoltenApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}