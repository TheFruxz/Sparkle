package de.fruxz.sparkle.server.component.update

import de.fruxz.sparkle.framework.extension.component
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.UPDATE_AVAILABLE
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeLightPurple
import de.fruxz.stacked.extension.newlines
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.hover
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class UpdateNotificationHandler(private val component: UpdateComponent = component(UpdateComponent::class)) : EventListener() {

	@EventHandler
	fun onJoin(event: PlayerJoinEvent) = with(event) {

		if (component.updateConfiguration.updateJoinNotifications && player.isOp) {
			val availableUpdates = UpdateComponent.updateStates.filter { it.value.type == UPDATE_AVAILABLE }

			if (availableUpdates.isNotEmpty()) {
				text {
					this + text("There are currently ").dyeGray()
					this + text("${availableUpdates.size} Updates") {
						dyeLightPurple()
						hover {
							text {
								this + text("List of available updates:").dyeGray()
								newlines(2)
								availableUpdates.keys.forEach {
									this + text(it.key.asString()).dyeLightPurple()
									this + text(Component.newline())
								}
								this + Component.newline()
								this + text {
									this + text("CLICK").style(NamedTextColor.GREEN, BOLD)
									this + text(" to view the list of apps").dyeGray()
								}
							}
						}
						clickEvent(ClickEvent.runCommand("/app list"))
					}
					this + text(" available to install!").dyeGray()
				}.notification(TransmissionAppearance.ATTENTION, player).display()
			}
		}

	}

}