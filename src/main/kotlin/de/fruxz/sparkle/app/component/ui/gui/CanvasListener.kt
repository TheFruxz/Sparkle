package de.fruxz.sparkle.app.component.ui.gui

import de.fruxz.sparkle.app.MoltenCache
import de.fruxz.sparkle.extension.data.ticks
import de.fruxz.sparkle.extension.paper.player
import de.fruxz.sparkle.extension.tasky.task
import de.fruxz.sparkle.runtime.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.runtime.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.structure.app.event.EventListener
import de.fruxz.sparkle.tool.display.canvas.CanvasFlag.*
import de.fruxz.sparkle.tool.display.canvas.CanvasSessionManager
import de.fruxz.sparkle.tool.timing.tasky.TemporalAdvice.Companion
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

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

			CanvasClickEvent(player, canvas, event.slot, event.view, event.isCancelled, event, event.click).let { internalEvent ->

				if (!internalEvent.callEvent()) event.isCancelled = true

				if (!event.isCancelled && !canvas.flags.contains(NO_CLICK_ACTIONS)) {

					MoltenCache.canvasActions[session.canvas]?.onClicks?.forEach { it.invoke(internalEvent) }

				}

			}

			if (canvas.flags.contains(NO_GRAB)) {
				val offhand = player.inventory.itemInOffHand.clone()

				event.isCancelled = true

				task(Companion.delayed(1.ticks)) {
					player.inventory.setItemInOffHand(offhand)
				}
			}

		}

	}

	@EventHandler
	fun onItemMove(event: InventoryMoveItemEvent) {
		val session = (event.source.viewers.toSet() + event.initiator.viewers + event.destination.viewers).mapNotNull {
			if (it is Player) CanvasSessionManager.getSession(it) else null
		}.firstOrNull()

		if (session != null) {
			val canvas = MoltenCache.canvas[session.canvas] ?: return

			if (canvas.flags.contains(NO_MOVE)) event.isCancelled = true

		}

	}

	@EventHandler
	fun onItemDrag(event: InventoryDragEvent) {
		val player = event.player
		val session = CanvasSessionManager.getSession(player)

		if (session != null) {
			val canvas = MoltenCache.canvas[session.canvas] ?: return

			if (canvas.flags.contains(NO_DRAG)) event.isCancelled = true

		}
	}

	@EventHandler
	fun onItemSwap(event: PlayerSwapHandItemsEvent) {
		val player = event.player
		val session = CanvasSessionManager.getSession(player)
		val open = player.openInventory.topInventory
		event.isCancelled = true

		if (session != null) {
			val canvas = MoltenCache.canvas[session.canvas] ?: return

			if (open.contains(event.mainHandItem) || open.contains(event.offHandItem)) {

				if (canvas.flags.contains(NO_SWAP)) event.isCancelled = true

			}

		}
	}

}