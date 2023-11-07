package de.fruxz.sparkle.server.component.keeper

import dev.fruxz.ascend.extension.catchException
import dev.fruxz.ascend.extension.container.withForEach
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.cache.CacheDepthLevel.DUMP
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceActions
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceTimes
import de.fruxz.sparkle.framework.infrastructure.service.ServiceIteration
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleCache
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

internal class KeeperService(override val vendor: App = sparkle) : Service {

	override val label = "Cache-Cleaner"

	override val serviceTimes = ServiceTimes(1.seconds, 20.minutes, false)

	override val serviceActions = ServiceActions(onStart = {
		serviceLogger.info("Hey! I'm starting to clean your App-Caches!")
	})

	override val iteration: ServiceIteration = {

		// Cleaning the individual caches of the different registered apps
		SparkleCache.registeredApps.withForEach {
			try {
				val level = DUMP
				appCache?.let {
					it.dropEverything(level)
					debugLog("removed appCache (level: ${level.name}) from app $label")
				}
			} catch (exception: Exception) {

				if (SparkleApp.debugMode) catchException(exception)

				debugLog("failed to clean appCache of '$label'${ if (SparkleApp.debugMode.not()) ", enable debugMode to see the stacktrace" else "" }!")

			}
		}

	}

}