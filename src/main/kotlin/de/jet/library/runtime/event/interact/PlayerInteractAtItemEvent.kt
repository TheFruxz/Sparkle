package de.jet.library.runtime.event.interact

import de.jet.library.tool.display.item.Item
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack

// TODO: 31.07.2021 build and run via component-task
data class PlayerInteractAtItemEvent(
	val whoInteract: Player,
	val itemStack: ItemStack,
	val material: Material,
	val action: Action,
	private var isCancelled: Boolean = false
) : JetPlayerInteractEvent, PlayerEvent(whoInteract, false), Cancellable {

	override fun isCancelled() = isCancelled

	override fun setCancelled(cancel: Boolean) {
		isCancelled = cancel
	}

	override var isDenied: Boolean? = null

	override fun getHandlers() = handlerList

	val item = Item(itemStack)

	companion object {

		private val handlerList = HandlerList()

		@JvmStatic
		fun getHandlerList() = handlerList
	}

}