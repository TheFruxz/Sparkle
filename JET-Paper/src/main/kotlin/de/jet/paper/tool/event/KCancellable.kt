package de.jet.paper.tool.event

import org.bukkit.event.Cancellable

interface KCancellable : Cancellable {

	override fun isCancelled() = eventCancelled

	override fun setCancelled(cancel: Boolean) {
		eventCancelled = cancel
	}

	var eventCancelled: Boolean

}