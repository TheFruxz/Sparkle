package de.fruxz.sparkle.server.component.ui.gui

import dev.fruxz.ascend.extension.container.modified
import dev.fruxz.ascend.extension.container.takeOrEmpty
import dev.fruxz.ascend.extension.empty
import dev.fruxz.ascend.extension.math.maxTo
import dev.fruxz.ascend.extension.math.minTo
import de.fruxz.sparkle.framework.effect.sound.SoundLibrary
import de.fruxz.sparkle.framework.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent.UpdateReason
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.effect.playEffect
import de.fruxz.sparkle.framework.extension.player
import de.fruxz.sparkle.framework.extension.time.minecraftTicks
import de.fruxz.sparkle.framework.extension.visual.ui.affectedItem
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.*
import de.fruxz.sparkle.framework.visual.canvas.pagination.CanvasPageInformation
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.SCROLL
import de.fruxz.sparkle.framework.visual.canvas.session.CanvasSessionManager
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
			val (pageState, renderedLines, utilizableLines, isLastPosition) = CanvasPageInformation.of(event.inventory, session)

			when (affectedItem?.getPersistent<Int>(PaginationType.CANVAS_BUTTON_SCROLL)) {
				0 -> {
					event.isCancelled = true
					if (pageState > 0) {
						canvas.update(player, data = session.parameters.modified {
							when {
								event.isShiftClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = 0
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS_PUNCH)
								}
								event.isLeftClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = pageState - 1
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS)
								}
								event.isRightClick -> {
									this[PaginationType.CANVAS_SCROLL_STATE] = (pageState - 3).minTo(0)
									player.playEffect(SoundLibrary.UI_BUTTON_PRESS_HEAVY)
								}
							}
						}, updateReason = when (canvas.pagination.base) {
							PAGED -> UpdateReason.PAGE_TURN
							SCROLL -> UpdateReason.SCROLL
							else -> UpdateReason.PLUGIN
						})
					}
				}
				1 -> {
					var parameters = session.parameters
					event.isCancelled = true

					when (canvas.pagination.base) {
						SCROLL -> {
							if (!isLastPosition) {

								when {
									event.isShiftClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (renderedLines-(utilizableLines-1))
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS_PUNCH)
									}
									event.isLeftClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (pageState + 1).maxTo(renderedLines)
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS)
									}
									event.isRightClick -> {
										parameters += PaginationType.CANVAS_SCROLL_STATE to (pageState + 3).maxTo(renderedLines)
										player.playEffect(SoundLibrary.UI_BUTTON_PRESS_HEAVY)
									}
								}

								canvas.update(player, data = parameters, updateReason = UpdateReason.SCROLL)

							}
						}
						PAGED -> {
							if (!isLastPosition) {
								parameters += PaginationType.CANVAS_SCROLL_STATE to (pageState + 1)
								canvas.update(player, data = parameters, updateReason = UpdateReason.PAGE_TURN)
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

				doSync(1.minecraftTicks) {
					player.inventory.setItemInOffHand(offhand) // workaround for offhand non-cancelable
				}
			}

			CanvasClickEvent(player, canvas, event.slot, event.view, event, event.click).let { internalEvent ->

				if (!internalEvent.callEvent()) event.isCancelled = true

				if (!canvas.flags.contains(NO_CLICK_ACTIONS)) {

					session.canvas.onClicks
						.let { (it[null].takeOrEmpty()) + (it[event.slot].takeOrEmpty()) }
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