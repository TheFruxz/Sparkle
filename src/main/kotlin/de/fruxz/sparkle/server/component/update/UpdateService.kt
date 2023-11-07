package de.fruxz.sparkle.server.component.update

import dev.fruxz.ascend.extension.empty
import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.extension.*
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.*
import de.fruxz.sparkle.framework.infrastructure.service.Service
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceActions
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceTimes
import de.fruxz.sparkle.framework.infrastructure.service.ServiceIteration
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.dyeYellow
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import kotlin.time.Duration.Companion.seconds

class UpdateService(private val component: UpdateComponent = component(UpdateComponent::class), override val vendor: App = sparkle) : Service {
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

					if (!(oldState == null && newState?.type == UP_TO_DATE)) {

						when (newState?.type) {
							UP_TO_DATE -> text {
								this + text("The app '").dyeGray()
								this + text(app.key.asString()).dyeYellow()
								this + text("' is now up to date again!").dyeGray()
							}.notification(TransmissionAppearance.GENERAL).display(alertReceivers)

							UPDATE_AVAILABLE -> text {
								this + text("The app '").dyeGray()
								this + text(app.key.asString()).dyeYellow()
								this + text("' can now be updated!").dyeGray()
							}.notification(TransmissionAppearance.GENERAL).display(alertReceivers)

							FAILED -> text {
								this + text("The app '").dyeGray()
								this + text(app.key.asString()).dyeYellow()
								this + text("' just failed to search for updates!").dyeGray()
							}.notification(TransmissionAppearance.GENERAL).display(alertReceivers)

							null -> empty()
						}

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