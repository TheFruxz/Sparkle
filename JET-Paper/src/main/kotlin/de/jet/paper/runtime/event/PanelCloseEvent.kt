package de.jet.paper.runtime.event

import de.jet.paper.tool.display.ui.panel.Panel
import de.jet.paper.tool.display.ui.panel.Panel.Companion.getPanel
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent

class PanelCloseEvent(
	val origin: InventoryCloseEvent,
	override val panel: Panel = origin.inventory.getPanel()!!,
	val player: Player = origin.player as Player,
) : InventoryEvent(origin.view), PanelEvent