package de.fruxz.sparkle.runtime.event

import de.fruxz.sparkle.app.component.events.EventsComponent
import de.fruxz.sparkle.tool.annotation.RequiresComponent
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

@RequiresComponent(EventsComponent::class)
class PlayerDamageByPlayerEvent(
	val attacked: Player,
	val attacker: Player,
	private var isCancelled: Boolean = false,
) : PlayerEvent(attacked, false), Cancellable {

	override fun isCancelled() = isCancelled

	override fun setCancelled(cancel: Boolean) {
		isCancelled = cancel
	}

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}