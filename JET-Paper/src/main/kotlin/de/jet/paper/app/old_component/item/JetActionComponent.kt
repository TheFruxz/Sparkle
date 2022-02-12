package de.jet.paper.app.old_component.item

import de.jet.jvm.extension.collection.replace
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.display.ui.get
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.get
import de.jet.paper.extension.lang
import de.jet.paper.extension.paper.createKey
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.task
import de.jet.paper.extension.timing.getCooldown
import de.jet.paper.extension.timing.isCooldownDecaying
import de.jet.paper.extension.timing.setCooldown
import de.jet.paper.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.structure.component.Component
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.item.action.ActionCooldownType.BUKKIT_MATERIAL
import de.jet.paper.tool.display.item.action.ActionCooldownType.JET_INFO
import de.jet.paper.tool.display.item.action.ItemAction
import de.jet.paper.tool.display.message.Transmission.Level.FAIL
import de.jet.paper.tool.display.ui.panel.PanelFlag
import de.jet.paper.tool.display.ui.panel.PanelFlag.*
import de.jet.paper.tool.timing.tasky.TemporalAdvice.Companion.instant
import org.bukkit.entity.Player
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGH
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryOpenEvent

internal class JetActionComponent(vendor: App = system) : Component(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Actions"

	private val handler = Handler(vendor)

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	private class Handler(override val vendor: App) : EventListener, Listener {

		private fun hasNoCooldown(player: Player, action: ItemAction<*>, item: Item): Boolean {
			return !player.isCooldownDecaying("item:${item.identity}:${action.eventClass.simpleName}")
		}

		private fun produceActionCooldown(item: Item, player: Player, action: ItemAction<*>) {
			val cooldown = action.cooldown

			if (cooldown != null && cooldown.ticks > 0U) {

				if (cooldown.type != BUKKIT_MATERIAL) {

					player.setCooldown("item:${item.identity}:${action.eventClass.simpleName}", cooldown.ticks.toInt())

				} else
					player.setCooldown(item.material, cooldown.ticks.toInt())

			}

		}

		private fun getFlags(item: Item?): Set<PanelFlag> {
			val panelIdentity = item?.identityObject
			return JetCache.registeredPanelFlags[panelIdentity?.identity] ?: emptySet()
		}

		private fun reactToActionCooldown(item: Item, player: Player, action: ItemAction<*>) {
			try {

				player.getCooldown("item:${item.identity}:${action.eventClass.simpleName}")?.let { cooldown ->
					val cancelType = action.cooldown?.type
					val remaining = cooldown.remainingTime

					if (cancelType != null) {

						if (cancelType == JET_INFO) {
							lang["component.jetAction.item.cooldown.jetInfo"]
								.replace("[remaining]" to remaining)
								.notification(FAIL, player).display()
						}

					}

				}

			} catch (_: NoSuchElementException) { }
		}

		@EventHandler
		fun inventoryClick(event: InventoryClickEvent) {
			val item = event.currentItem?.item
			val player = event.whoClicked as Player
			val inventory = event.clickedInventory

			// item action

			item?.clickAction?.let { action ->

				if (hasNoCooldown(player, action, item)) {

					if (action.stop)
						event.isCancelled = true

					produceActionCooldown(item, player, action)

					task(instant(async = action.async)) {
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

		@EventHandler(priority = HIGH)
		fun inventoryOpen(event: InventoryOpenEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_OPEN_ABLE))
				event.isCancelled = true
		}

		@EventHandler(priority = HIGH)
		fun inventoryClose(event: InventoryCloseEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_CLOSE_ABLE))
				event.player.openInventory(event.inventory)
		}

		@EventHandler(priority = HIGH)
		fun inventoryDrag(event: InventoryDragEvent) {
			if (getFlags(event.inventory[4]?.item).contains(NOT_DRAG_ABLE))
				event.isCancelled = true
		}

		@EventHandler(priority = HIGH)
		fun inventoryMove(event: InventoryMoveItemEvent) {
			if ((getFlags(event.destination[4]?.item) + getFlags(event.initiator[4]?.item)).contains(NOT_MOVE_ABLE))
				event.isCancelled = true
		}

		@EventHandler(priority = HIGH)
		fun playerInteractAtItem(event: PlayerInteractAtItemEvent) {
			with(event) {
				item.interactAction?.let { action ->
					if (hasNoCooldown(player, action, item)) {
						if (action.stop) {
							event.interactedItem = DENY
							event.interactedBlock = DENY
						}
						produceActionCooldown(item, player, action)
						task(instant(async = action.async), vendor = vendor) {
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