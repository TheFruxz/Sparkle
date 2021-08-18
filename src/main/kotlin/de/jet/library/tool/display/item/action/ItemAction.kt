package de.jet.library.tool.display.item.action

import de.jet.library.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.item.action.ActionCooldownType.JET_SILENT
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*
import kotlin.time.Duration

// Cooldown

enum class ActionCooldownType {
	BUKKIT_MATERIAL, JET_SILENT, JET_INFO;
}

data class ActionCooldown(
	val ticks: ULong,
	val type: ActionCooldownType = JET_SILENT,
) {
	constructor(ticks: Number, type: ActionCooldownType = JET_SILENT) : this(ticks.toLong().toULong(), type)
}

// ItemAction

sealed interface ItemAction<T : Event> {

	var action: T.() -> Unit

	var async: Boolean

	var stop: Boolean

	var cooldown: ActionCooldown?

}

data class ItemClickAction(
	override var action: InventoryClickEvent.() -> Unit,
	override var async: Boolean = true,
	override var stop: Boolean = true,
	override var cooldown: ActionCooldown? = null
) : ItemAction<InventoryClickEvent>

data class ItemInteractAction(
	override var action: PlayerInteractAtItemEvent.() -> Unit,
	override var async: Boolean = true,
	override var stop: Boolean = true,
	override var cooldown: ActionCooldown? = null
) : ItemAction<PlayerInteractAtItemEvent>
