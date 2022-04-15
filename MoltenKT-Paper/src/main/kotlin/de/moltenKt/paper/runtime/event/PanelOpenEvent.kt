package de.moltenKt.paper.runtime.event

import de.moltenKt.paper.tool.display.ui.panel.Panel
import de.moltenKt.paper.tool.display.ui.panel.Panel.Companion.getPanel
import de.moltenKt.paper.tool.event.KCancellable
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class PanelOpenEvent(
	val origin: InventoryOpenEvent,
	override val panel: Panel = origin.inventory.getPanel()!!,
	override var eventCancelled: Boolean = false,
	val player: Player = origin.player as Player,
) : InventoryEvent(origin.view), KCancellable, PanelEvent