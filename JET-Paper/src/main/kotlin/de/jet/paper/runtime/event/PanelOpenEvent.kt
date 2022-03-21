package de.jet.paper.runtime.event

import de.jet.paper.tool.display.ui.panel.Panel
import de.jet.paper.tool.display.ui.panel.Panel.Companion.getPanel
import de.jet.paper.tool.event.KCancellable
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class PanelOpenEvent(
	val origin: InventoryOpenEvent,
	override val panel: Panel = origin.inventory.getPanel()!!,
	override var eventCancelled: Boolean = false,
	val player: Player = origin.player as Player,
) : InventoryEvent(origin.view), KCancellable, PanelEvent