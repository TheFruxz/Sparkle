package de.fruxz.sparkle.server.component.update

import de.fruxz.ascend.extension.empty
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.framework.extension.apps
import de.fruxz.sparkle.framework.extension.consoleSender
import de.fruxz.sparkle.framework.extension.onlinePlayers
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.*
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceActions
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceTimes
import de.fruxz.sparkle.framework.infrastructure.service.ServiceIteration
import de.fruxz.sparkle.framework.visual.message.Transmission.Level
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import kotlin.time.Duration.Companion.seconds

class UpdateService(component: UpdateComponent, override val vendor: App = sparkle) : Service {
	override val label = "Updates"
	override val serviceTimes = ServiceTimes(10.seconds, component.updateConfiguration.updateCheckIntervallSeconds.seconds)
	override val serviceActions = ServiceActions()
	override val iteration: ServiceIteration = {
		val alertReceivers = onlinePlayers.filter { it.isOp }.toSet() + consoleSender

		if (component.updateConfiguration.updateUpdateNotifications) {

			apps.forEach { app ->
				val oldState = UpdateComponent.updateStates[app]
				val newState = UpdateManager.getUpdate(app)

				if (oldState?.type != newState?.type) {

					if (newState != null) {
						UpdateComponent.updateStates += app to newState
					} else
						UpdateComponent.updateStates -= app

					when (newState?.type) {
						UP_TO_DATE -> text {
							this + text("The app '").dyeGray()
							this + text(app.key.asString()).dyeYellow()
							this + text("' is now up to date again!").dyeGray()
						}.notification(Level.GENERAL).display(alertReceivers)

						UPDATE_AVAILABLE -> text {
							this + text("The app '").dyeGray()
							this + text(app.key.asString()).dyeYellow()
							this + text("' can now be updated!").dyeGray()
						}.notification(Level.GENERAL).display(alertReceivers)

						FAILED -> text {
							this + text("The app '").dyeGray()
							this + text(app.key.asString()).dyeYellow()
							this + text("' just failed to search for updates!").dyeGray()
						}.notification(Level.GENERAL).display(alertReceivers)

						null -> empty()
					}

				}

			}

			lastUpdateCheck = Calendar.now()

		}

	}

	companion object {

		var lastUpdateCheck: Calendar? = null
			private set

	}

}