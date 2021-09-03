package de.jet.minecraft.app.component.events

import de.jet.library.extension.checkAllObjects
import de.jet.library.extension.forceCast
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.extension.system
import de.jet.minecraft.runtime.event.PlayerDamageByPlayerEvent
import de.jet.minecraft.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.minecraft.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.structure.component.Component.RunType.AUTOSTART_MUTABLE
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

internal class JetEventsComponent(vendor: App = system) : Component(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Events"

	private val handler by lazy {
		Handler(vendor)
	}

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	private class Handler(override val vendor: App) : EventListener {

		@EventHandler
		fun entityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

			if (checkAllObjects(event.entity, event.damager) { this is Player }) {
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

		@EventHandler
		fun playerInteractEvent(event: PlayerInteractEvent) {
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

}