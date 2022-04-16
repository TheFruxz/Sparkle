package de.moltenKt.paper.app.component.keeper

import de.moltenKt.core.extension.catchException
import de.moltenKt.core.extension.container.withForEach
import de.moltenKt.core.extension.display.display
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.extension.timing.isOver
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel.DUMP
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level

internal class KeeperService : Service {

	override val vendor = system

	override val thisIdentity = "iKeeper"

	override val temporalAdvice = TemporalAdvice.ticking(20, 20L*60*5, false)

	override val onStart: Tasky.() -> Unit = {
		sectionLog.log(Level.INFO, "Hey! I'm starting to clean your JET! Wait, what's JET?")
	}

	override val process: Tasky.() -> Unit = {

		// Cleaning Cooldowns

		MoltenCache.livingCooldowns.toList().forEach { (key, value) ->

			if (value.isOver) {
				MoltenCache.livingCooldowns.remove(key)
				debugLog("removed livingCooldown $key, because, it was over; remaining: ${value.remainingTime.display()}")
			}

		}

		MoltenCache.registeredApplications.withForEach {
			try {
				val level = DUMP
				appCache.dropEverything(level)
				debugLog("removed appCache (level: ${level.name}) from app $appLabel")
			} catch (exception: Exception) {

				if (de.moltenKt.paper.app.MoltenApp.debugMode)
					catchException(exception)

				debugLog("failed to clean appCache of '$appLabel'${ if (de.moltenKt.paper.app.MoltenApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}