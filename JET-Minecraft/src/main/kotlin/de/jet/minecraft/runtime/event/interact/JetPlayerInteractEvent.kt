package de.jet.minecraft.runtime.event.interact

import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

interface JetPlayerInteractEvent {

	val origin: PlayerInteractEvent

	var interactedBlock: Event.Result

	var interactedItem: Event.Result

}