package de.jet.library.runtime.event

import de.jet.library.tool.display.message.Transmission
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerReceiveInterchangeEvent(
	player: Player,
	val transmission: Transmission,
	var isCancelled: Boolean,
) : PlayerEvent(player, true) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}