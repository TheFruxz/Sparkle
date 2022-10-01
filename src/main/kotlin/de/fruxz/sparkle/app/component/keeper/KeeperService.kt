package de.fruxz.sparkle.app.component.keeper

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.container.withForEach
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.structure.app.cache.CacheDepthLevel.DUMP
import de.fruxz.sparkle.structure.service.Service
import de.fruxz.sparkle.tool.timing.tasky.Tasky
import de.fruxz.sparkle.tool.timing.tasky.TemporalAdvice
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
		SparkleCache.registeredApps.withForEach {
			try {
				val level = DUMP
				appCache.dropEverything(level)
				debugLog("removed appCache (level: ${level.name}) from app $label")
			} catch (exception: Exception) {

				if (de.fruxz.sparkle.app.SparkleApp.debugMode) catchException(exception)

				debugLog("failed to clean appCache of '$label'${ if (de.fruxz.sparkle.app.SparkleApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}