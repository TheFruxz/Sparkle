package de.fruxz.sparkle.server.component.ui.gui

import de.fruxz.ascend.extension.container.edited
import de.fruxz.ascend.extension.empty
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.maxTo
import de.fruxz.ascend.extension.math.minTo
import de.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.sparkle.framework.effect.sound.SoundLibrary
import de.fruxz.sparkle.framework.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.framework.extension.coroutines.task
import de.fruxz.sparkle.framework.extension.data.ticks
import de.fruxz.sparkle.framework.extension.effect.playEffect
import de.fruxz.sparkle.framework.extension.player
import de.fruxz.sparkle.framework.extension.visual.ui.affectedItem
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.scheduler.TemporalAdvice.Companion
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.*
import de.fruxz.sparkle.framework.visual.canvas.CanvasSessionManager
import de.fruxz.sparkle.framework.visual.canvas.PaginationType
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.SCROLL
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

			val canvas = session?.canvas

			if (canvas != null) {

				if (canvas.flags.contains(NO_CLOSE)) {
					canvas.display(player)
				} else {
					canvas.onClose(
						CanvasCloseEvent(
							player,
							canvas,
							event.view,
							event
						)
					)
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
			val canvas = session.canvas
			val affectedItem = event.affectedItem?.item
			val scrollState = session.parameters[PaginationType.CANVAS_SCROLL_STATE]?.takeIfInstance<Int>() ?: 0
			val linesOfContent = ceilToInt(canvas.content.keys.max().toDouble() / 8)
			val inventoryLines = ceilToInt(event.inventory.size.toDouble() / 9)

			when (affectedItem?.getPersistent<Int>(PaginationType.CANVAS_BUTTON_SCROLL)) {
				0 -> {
					event.isCancelled = true
					if (scrollState > 0) {
						canvas.display(player, data = session.parameters.edited { // todo instead of display use update
							when {
								event.isShiftClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = 0
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS_PUNCH)
								}
								event.isLeftClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = scrollState - 1
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS)
								}
								event.isRightClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = (scrollState - 3).minTo(0)
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS_HEAVY)
								}
							}
						})
					}
				}
				1 -> {
					var parameters = session.parameters
					event.isCancelled = true

					when (canvas.pagination.base) {
						SCROLL -> {
							if ((scrollState + inventoryLines) <= linesOfContent) {

								when {
									event.isShiftClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (linesOfContent-(inventoryLines-1))
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS_PUNCH)
									}
									event.isLeftClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (scrollState + 1).maxTo(linesOfContent)
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS)
									}
									event.isRightClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (scrollState + 3).maxTo(linesOfContent)
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS_HEAVY)
									}
								}

								canvas.display(player, data = parameters)
							}
						}
						PAGED -> {
							if (scrollState <= (linesOfContent / ceilToInt((event.inventory.size.toDouble() / 9)-1)) - 1) {
								parameters += PaginationType.CANVAS_SCROLL_STATE to (scrollState + 1)
								canvas.display(player, data = parameters)
							}
						}
						else -> empty()
					}


				}
				else -> empty()
			}

			if (canvas.flags.contains(NO_GRAB)) {
				val offhand = player.inventory.itemInOffHand.clone()

				event.isCancelled = true

				task(Companion.delayed(1.ticks)) {
					player.inventory.setItemInOffHand(offhand)
				}
			}

			CanvasClickEvent(player, canvas, event.slot, event.view, event, event.click).let { internalEvent ->

				if (!internalEvent.callEvent()) event.isCancelled = true

				if (!canvas.flags.contains(NO_CLICK_ACTIONS)) {

					session.canvas.onClicks
						.let { (it[null] ?: emptyList()) + (it[event.slot] ?: emptyList()) }
						.forEach { it.invoke(internalEvent) }

				}

			}

		}

	}

	@EventHandler
	fun onItemMove(event: InventoryMoveItemEvent) {
		val session =
			(event.source.viewers.toSet() + event.initiator.viewers + event.destination.viewers).firstNotNullOfOrNull {
				if (it is Player) CanvasSessionManager.getSession(it) else null
			}

		if (session != null) {
			val canvas = session.canvas

			if (canvas.flags.contains(NO_MOVE)) event.isCancelled = true

		}

	}

	@EventHandler
	fun onItemDrag(event: InventoryDragEvent) {
		val player = event.player
		val session = CanvasSessionManager.getSession(player)

		if (session != null) {
			val canvas = session.canvas

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
			val canvas = session.canvas

			if (open.contains(event.mainHandItem) || open.contains(event.offHandItem)) {

				if (canvas.flags.contains(NO_SWAP)) event.isCancelled = true

			}

		}
	}

}