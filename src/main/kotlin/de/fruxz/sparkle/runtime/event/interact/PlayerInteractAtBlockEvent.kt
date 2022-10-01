package de.fruxz.sparkle.runtime.event.interact

import de.fruxz.sparkle.app.component.events.EventsComponent
import de.fruxz.sparkle.tool.annotation.RequiresComponent
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event.Result.DEFAULT
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent

@RequiresComponent(EventsComponent::class)
data class PlayerInteractAtBlockEvent(
	val whoInteract: Player,
	val block: Block,
	val material: Material,
	val action: Action,
	override val origin: PlayerInteractEvent,
	override var interactedBlock: Result = DEFAULT,
	override var interactedItem: Result = DEFAULT,
) : SparklePlayerInteractEvent, PlayerEvent(whoInteract, false) {

	override fun getHandlers() = handlerList

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList

	}

}