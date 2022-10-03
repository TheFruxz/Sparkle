package de.fruxz.sparkle.server.component.keeper

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.container.withForEach
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel.DUMP
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.util.extension.debugLog
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.scheduler.Tasky
import de.fruxz.sparkle.framework.util.scheduler.TemporalAdvice
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

				if (SparkleApp.debugMode) catchException(exception)

				debugLog("failed to clean appCache of '$label'${ if (SparkleApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}