package de.jet.app.component.system

import de.jet.app.JetCache.livingCooldowns
import de.jet.library.extension.debugLog
import de.jet.library.extension.display.display
import de.jet.library.extension.system
import de.jet.library.extension.timing.isOver
import de.jet.library.structure.app.App
import de.jet.library.structure.component.Component
import de.jet.library.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.library.structure.service.Service
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.timing.tasky.Tasky
import de.jet.library.tool.timing.tasky.TemporalAdvice
import java.util.logging.Level

class JetKeeperComponent(vendor: App = system) : Component(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Keeper"

	private val service = KeeperService(vendor)

	override fun start() {
		service.let { with(vendor) {
			register(it)
			start(it)
		} }
	}

	override fun stop() {
		service.let { with(vendor) {
			stop(it)
			unregister(it)
		} }
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
					debugLog("removed livingCooldown $key, because, it was over; remaining: ${value.remainingCooldown.display()}")
				}

			}

		}

	}

}