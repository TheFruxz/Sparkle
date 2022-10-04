package de.fruxz.sparkle.framework.event.interact

import org.bukkit.event.Event
import org.bukkit.event.Event.Result.ALLOW
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.player.PlayerInteractEvent

interface SparklePlayerInteractEvent {

	val origin: PlayerInteractEvent

	var interactedBlock: Event.Result

	var interactedItem: Event.Result

	companion object {

		fun <T : SparklePlayerInteractEvent> T.denyInteraction() {
			this.interactedBlock = DENY
			this.interactedItem = DENY
		}

		fun <T : SparklePlayerInteractEvent> T.allowInteraction() {
			this.interactedBlock = ALLOW
			this.interactedItem = ALLOW
		}

	}

}