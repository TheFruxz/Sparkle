package de.jet.app.component.events

import de.jet.library.extension.checkAllObjects
import de.jet.library.extension.forceCast
import de.jet.library.runtime.event.PlayerDamageByPlayerEvent
import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent

class JetEventsComponent(vendor: App) : Component(vendor) {

	override val id = "JETEvents"

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

	}

}