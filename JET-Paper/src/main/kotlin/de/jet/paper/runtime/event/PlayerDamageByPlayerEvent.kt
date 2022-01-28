package de.jet.paper.runtime.event

import de.jet.paper.app.component.events.JetEventsComponent
import de.jet.paper.tool.annotation.RequiresComponent
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

@RequiresComponent(JetEventsComponent::class)
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