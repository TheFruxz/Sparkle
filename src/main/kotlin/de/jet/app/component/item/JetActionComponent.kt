package de.jet.app.component.item

import de.jet.app.JetCache
import de.jet.library.JET
import de.jet.library.extension.collection.replace
import de.jet.library.extension.display.notification
import de.jet.library.extension.display.ui.get
import de.jet.library.extension.display.ui.item
import de.jet.library.extension.lang
import de.jet.library.extension.paper.createKey
import de.jet.library.extension.system
import de.jet.library.extension.tasky.async
import de.jet.library.extension.tasky.sync
import de.jet.library.extension.tasky.task
import de.jet.library.extension.tasky.wait
import de.jet.library.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.item.action.ActionCooldownType.*
import de.jet.library.tool.display.item.action.ItemAction
import de.jet.library.tool.display.message.Transmission.Level.FAIL
import de.jet.library.tool.display.ui.panel.PanelFlag
import de.jet.library.tool.display.ui.panel.PanelFlag.*
import de.jet.library.tool.tasky.TemporalAdvice
import de.jet.library.tool.tasky.TemporalAdvice.Companion
import org.bukkit.entity.Player
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.NoSuchElementException
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal class JetActionComponent(vendor: App) : Component(vendor, true) {

	override val thisIdentity = "Actions"

	private val handler = Handler(vendor)

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	private class Handler(override val vendor: App) : EventListener, Listener {

		private fun hasNoCooldown(player: Player, item: Item): Boolean {
			return JetCache.runningCooldowns[player]?.none { it.id == item.identity } ?: true
		}

		private fun produceActionCooldown(item: Item, player: Player, action: ItemAction<*>) {
			val cooldown = action.cooldown

			if (cooldown != null && cooldown.ticks > 0U) {

				if (cooldown.type != BUKKIT_MATERIAL) {

					JetCache.runningCooldowns[player] = JetCache.runningCooldowns[player]?.apply {
						add(item.identityObject)
					} ?: mutableSetOf()

					JetCache.runningCooldownDestinations[player to item.identityObject] = Calendar.getInstance().apply {
						add(Calendar.MILLISECOND, 50*cooldown.ticks.toInt())
					}

					async(JET.appInstance, Companion.delayed(cooldown.ticks.toLong())) {
						sync {
							JetCache.runningCooldowns[player] = JetCache.runningCooldowns[player]?.apply {
								remove(item.identityObject)
							} ?: mutableSetOf()
						}
					}

				} else
					player.setCooldown(item.material, cooldown.ticks.toInt())

			}

		}

		private fun getFlags(item: Item?): Set<PanelFlag> {
			val panelIdentity = item?.identityObject
			return JetCache.registeredPanelFlags[panelIdentity?.id] ?: emptySet()
		}

		@ExperimentalTime
		private fun reactToActionCooldown(item: Item, player: Player, action: ItemAction<*>) {
			try {

				JetCache.runningCooldowns[player]?.first { it.id == item.identity }?.let {
					val cooldown = action.cooldown
					val remaining =
						Duration.milliseconds((JetCache.runningCooldownDestinations[player to it] ?: Calendar.getInstance()).timeInMillis - Calendar.getInstance().timeInMillis).toString(SECONDS)

					if (cooldown != null) {

						if (cooldown.type == JET_INFO) {
							lang("component.jetAction.item.cooldown.jetInfo")
								.replace("[remaining]" to remaining)
								.notification(FAIL, player).display()
						}

					}

				}

			} catch (exception: NoSuchElementException) { }
		}

		@ExperimentalTime
		@EventHandler
		fun inventoryClick(event: InventoryClickEvent) {
			val item = event.currentItem?.item
			val player = event.whoClicked as Player
			val inventory = event.clickedInventory

			// item action

			item?.clickAction?.let { action ->

				if (hasNoCooldown(player, item)) {

					if (action.stop)
						event.isCancelled = true

					produceActionCooldown(item, player, action)

					task(vendor, TemporalAdvice.instant(async = action.async)) {
						action.action(event)
					}

				} else {
					event.isCancelled = true
					reactToActionCooldown(item, player, action)
				}

			}

			// inventory stuff

			if (inventory == event.view.topInventory) {

				if (getFlags(inventory[4]?.item).contains(NOT_CLICK_ABLE)) {
					event.isCancelled = true
				}

				if (event.currentItem?.let { it.item.dataGet(system.createKey("panelBorder")) == 1 } == true) {

					// everytime when the panelBorder property is set, it wants to be protected!
					event.isCancelled = true

				}

			}

		}

		@EventHandler
		fun inventoryOpen(event: InventoryOpenEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_OPEN_ABLE))
				event.isCancelled = true
		}

		@EventHandler
		fun inventoryClose(event: InventoryCloseEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_CLOSE_ABLE))
				event.player.openInventory(event.inventory)
		}

		@EventHandler
		fun inventoryDrag(event: InventoryDragEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_DRAG_ABLE))
				event.isCancelled = true
		}

		@EventHandler
		fun inventoryMove(event: InventoryMoveItemEvent) {
			if ((getFlags(event.destination[4]?.item) + getFlags(event.initiator[4]?.item)).contains(NOT_MOVE_ABLE))
				event.isCancelled = true
		}

		@ExperimentalTime
		@EventHandler
		fun playerInteractAtItem(event: PlayerInteractAtItemEvent) {
			with(event) {
				item.interactAction?.let { action ->

					if (hasNoCooldown(player, item)) {

						if (action.stop) {
							event.interactedItem = DENY
							event.interactedBlock = DENY
						}

						produceActionCooldown(item, player, action)

						task(vendor, TemporalAdvice.instant(async = action.async)) {
							action.action(event)
						}

					} else {
						event.isCancelled = true
						event.origin.isCancelled = true
						event.interactedItem = DENY
						event.interactedBlock = DENY
						reactToActionCooldown(item, player, action)
					}

				}
			}
		}

	}

}