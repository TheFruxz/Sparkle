package de.moltenKt.paper.app.component.keeper

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.container.withForEach
import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.cache.CacheDepthLevel.DUMP
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

internal class KeeperService : Service {

	override val vendor = system

	override val label = "iKeeper"

	override val temporalAdvice = TemporalAdvice.ticking(1.seconds, 20.minutes, false)

	override val onStart: Tasky.() -> Unit = {
		sectionLog.log(Level.INFO, "Hey! I'm starting to clean your App-Caches!")
	}

	override val process: Tasky.() -> Unit = {

		// Cleaning the individual caches of the different registered apps
		MoltenCache.registeredApps.withForEach {
			try {
				val level = DUMP
				appCache.dropEverything(level)
				debugLog("removed appCache (level: ${level.name}) from app $label")
			} catch (exception: Exception) {

				if (MoltenApp.debugMode) catchException(exception)

				debugLog("failed to clean appCache of '$label'${ if (MoltenApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}