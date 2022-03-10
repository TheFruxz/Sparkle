package de.jet.paper.app.component.events

import de.jet.jvm.extension.all
import de.jet.jvm.extension.forceCast
import de.jet.paper.extension.display.ui.getPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.runtime.event.PanelClickEvent
import de.jet.paper.runtime.event.PlayerDamageByPlayerEvent
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

internal class EventsListener : EventListener() {

	@EventHandler(priority = HIGHEST)
	fun onInventoryClick(event: InventoryClickEvent) {
		val player = event.whoClicked as Player
		val item = event.currentItem?.takeIf { it.hasItemMeta() }?.item ?: event.cursor?.takeIf { it.hasItemMeta() }?.item
		val inventory = event.clickedInventory
		val action = event.action
		val slot = event.slot
		val slotType = event.slotType

		if (inventory != null) {
			val panel = inventory.getPanel()

			if (panel != null) {

				PanelClickEvent(player, panel, action, slot, slotType, item).callEvent()

			}

		}

	}

	@EventHandler(priority = HIGHEST)
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		if (all(event.entity, event.damager) { this is Player }) {
			val internal = PlayerDamageByPlayerEvent(
				attacked = event.entity.forceCast(),
				attacker = event.damager.forceCast(),
				event.isCancelled,
			)

			if (internal.callEvent()) {
				event.isCancelled = internal.isCancelled
			}

		}
	}

	@EventHandler(priority = HIGHEST)
	fun onPlayerInteract(event: PlayerInteractEvent) {
		val player = event.player
		val item = event.item?.item
		val material = event.material
		val block = event.clickedBlock

		when {
			event.hasItem() && item != null -> {

				@Suppress("DEPRECATION") val internal = PlayerInteractAtItemEvent(
					whoInteract = player,
					item = item,
					material = material,
					action = event.action,
					isCancelled = event.isCancelled,
					origin = event,
					interactedBlock = event.useInteractedBlock(),
					interactedItem = event.useItemInHand(),
				)

				if (internal.callEvent()) {
					event.isCancelled = internal.isCancelled

					event.setUseInteractedBlock(internal.interactedBlock)
					event.setUseItemInHand(internal.interactedItem)

				}

			}
			event.hasBlock() && block != null -> {

				@Suppress("DEPRECATION") val internal = PlayerInteractAtBlockEvent(
					whoInteract = player,
					block = block,
					material = material,
					action = event.action,
					isCancelled = event.isCancelled,
					origin = event,
					interactedBlock = event.useInteractedBlock(),
					interactedItem = event.useItemInHand(),
				)

				if (internal.callEvent()) {
					event.isCancelled = internal.isCancelled

					event.setUseInteractedBlock(internal.interactedBlock)
					event.setUseItemInHand(internal.interactedItem)

				}

			}
		}
	}

}