package de.fruxz.sparkle.server.component.events

import de.fruxz.ascend.extension.all
import de.fruxz.ascend.extension.forceCast
import de.fruxz.sparkle.framework.event.PlayerDamageByPlayerEvent
import de.fruxz.sparkle.framework.event.interact.PlayerInteractAtBlockEvent
import de.fruxz.sparkle.framework.event.interact.PlayerInteractAtItemEvent
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

internal class EventsListener : EventListener() {

	@EventHandler(priority = HIGHEST)
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		if (all(event.entity, event.damager) { it is Player }) {
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

		if (event.hasItem() && item != null) {

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

		if (event.hasBlock() && block != null) {

			val internal = PlayerInteractAtBlockEvent(
				whoInteract = player,
				block = block,
				material = material,
				action = event.action,
				origin = event,
				interactedBlock = event.useInteractedBlock(),
				interactedItem = event.useItemInHand(),
			)

			if (internal.callEvent()) {

				event.setUseInteractedBlock(internal.interactedBlock)
				event.setUseItemInHand(internal.interactedItem)

			}

		}
	}

}