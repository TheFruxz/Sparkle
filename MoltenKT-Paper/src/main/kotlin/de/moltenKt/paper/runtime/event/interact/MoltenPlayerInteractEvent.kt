package de.moltenKt.paper.runtime.event.interact

import org.bukkit.event.Event
import org.bukkit.event.Event.Result.ALLOW
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.player.PlayerInteractEvent

interface MoltenPlayerInteractEvent {

	val origin: PlayerInteractEvent

	var interactedBlock: Event.Result

	var interactedItem: Event.Result

	fun MoltenPlayerInteractEvent.denyInteraction() {
		interactedBlock = DENY
		interactedItem = DENY
	}

	fun MoltenPlayerInteractEvent.allowInteraction() {
		interactedBlock = ALLOW
		interactedItem = ALLOW
	}

}