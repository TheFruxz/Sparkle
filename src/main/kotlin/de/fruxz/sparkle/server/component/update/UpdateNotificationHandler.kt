package de.fruxz.sparkle.server.component.update

import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.UPDATE_AVAILABLE
import de.fruxz.sparkle.framework.visual.message.Transmission.Level
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeLightPurple
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class UpdateNotificationHandler(private val updateComponent: UpdateComponent) : EventListener() {

	@EventHandler
	fun onJoin(event: PlayerJoinEvent) = with(event) {

		if (updateComponent.updateConfiguration.updateJoinNotifications && player.isOp) {
			val availableUpdates = UpdateComponent.updateStates.count { it.value.type == UPDATE_AVAILABLE }

			if (availableUpdates > 0) {
				text {
					this + text("There are currently ").dyeGray()
					this + text("$availableUpdates Updates").dyeLightPurple()
					this + text(" available to install!").dyeGray()
				}.notification(Level.ATTENTION, player).display()
			}
		}

	}

}