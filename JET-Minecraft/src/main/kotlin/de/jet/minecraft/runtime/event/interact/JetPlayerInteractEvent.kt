package de.jet.minecraft.runtime.event.interact

import org.bukkit.event.Event
import org.bukkit.event.Event.Result.ALLOW
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.player.PlayerInteractEvent

interface JetPlayerInteractEvent {

	val origin: PlayerInteractEvent

	var interactedBlock: Event.Result

	var interactedItem: Event.Result

	fun JetPlayerInteractEvent.denyInteraction() {
		interactedBlock = DENY
		interactedItem = DENY
	}

	fun JetPlayerInteractEvent.allowInteraction() {
		interactedBlock = ALLOW
		interactedItem = ALLOW
	}

}