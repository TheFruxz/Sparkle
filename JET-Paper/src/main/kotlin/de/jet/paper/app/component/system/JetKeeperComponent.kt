package de.jet.paper.app.component.system

import de.jet.jvm.extension.catchException
import de.jet.jvm.extension.collection.withForEach
import de.jet.jvm.extension.display.display
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetApp
import de.jet.paper.app.JetCache.livingCooldowns
import de.jet.paper.app.JetCache.registeredApplications
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.system
import de.jet.paper.extension.timing.isOver
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.cache.CacheDepthLevel.DUMP
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.timing.tasky.Tasky
import de.jet.paper.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level

class JetKeeperComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Keeper"

	override fun component() {
		service(KeeperService(vendor))
	}

	class KeeperService(override val vendor: Identifiable<App>) : Service {

		override val thisIdentity = "iKeeper"

		override val temporalAdvice = TemporalAdvice.ticking(20, 20L*60*5, false)

		override val onStart: Tasky.() -> Unit = {
			sectionLog.log(Level.INFO, "Hey! I'm starting to clean your JET!")
		}

		override val process: Tasky.() -> Unit = {

			// Cleaning CoolDowns

			livingCooldowns.toList().forEach { (key, value) ->

				if (value.isOver) {
					livingCooldowns.remove(key)
					debugLog("removed livingCooldown $key, because, it was over; remaining: ${value.remainingTime.display()}")
				}

			}

			registeredApplications.withForEach {
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

}