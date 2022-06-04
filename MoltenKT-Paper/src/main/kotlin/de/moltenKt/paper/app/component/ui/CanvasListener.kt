package de.moltenKt.paper.app.component.ui

import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.paper.player
import de.moltenKt.paper.runtime.event.canvas.CanvasClickEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasCloseEvent
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.tool.display.canvas.CanvasFlag.*
import de.moltenKt.paper.tool.display.canvas.CanvasSessionManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

internal class CanvasListener : EventListener() {

	@EventHandler
	fun onClose(event: InventoryCloseEvent) {
		val player = event.player

		if (player is Player) {

			val session = CanvasSessionManager.getSession(player)

			val canvas = MoltenCache.canvas[session?.canvas]

			if (canvas != null) {

				if (canvas.flags.contains(NO_CLOSE)) {
					canvas.display(player)
				} else {
					MoltenCache.canvasActions[session?.canvas]?.onClose?.let {
						it(
							CanvasCloseEvent(
								player,
								canvas,
								event.view,
								event
							)
						)
					}
					CanvasSessionManager.removeSession(player)
				}

			}

		}

	}

	@EventHandler
	fun onClick(event: InventoryClickEvent) {
		val player = event.player
		val session = CanvasSessionManager.getSession(player)

		if (session != null) {
			val canvas = MoltenCache.canvas[session.canvas] ?: return

			CanvasClickEvent(player, canvas, event.slot, event.view, event.isCancelled, event).let { internalEvent ->

				if (!internalEvent.callEvent()) event.isCancelled = true

				if (!event.isCancelled && !canvas.flags.contains(NO_CLICK_ACTIONS)) {

					MoltenCache.canvasActions[session.canvas]?.onClicks?.forEach { it.invoke(internalEvent) }

				}

				if (canvas.flags.contains(NO_GRAB)) event.isCancelled = true

			}

		}

	}

}