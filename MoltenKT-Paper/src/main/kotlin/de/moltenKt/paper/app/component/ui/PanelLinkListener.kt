package de.moltenKt.paper.app.component.ui

import de.moltenKt.jvm.extension.container.firstOrNull
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.runtime.event.PanelClickEvent
import de.moltenKt.paper.runtime.event.PanelCloseEvent
import de.moltenKt.paper.runtime.event.PanelOpenEvent
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag.*
import org.bukkit.event.EventHandler

internal class PanelLinkListener : EventListener() {

	@EventHandler
	fun onPanelClick(event: PanelClickEvent) {
		val panel = event.panel
		val flags = panel.panelFlags

		if (flags.contains(NO_GRAB) || (!flags.contains(NO_BORDER_PROTECTION) && (panel.isBorder(event.clickedSlot) || panel.isBorder(event.clickedItem)))) {
			event.isCancelled = true
		}

		if (!flags.contains(NO_INTERACT)) {
			MoltenCache.panelInteractions
				.firstOrNull { it.key.identity == panel.identity }?.value
				?.forEach { clickAction -> event.apply(clickAction) }
		}

	}

	@EventHandler
	fun onPanelOpen(event: PanelOpenEvent) {
		val panel = event.panel

		if (!panel.panelFlags.contains(NO_OPEN)) {
			panel.onOpenEvent(event)
		} else
			event.isCancelled = true

	}

	@EventHandler
	fun onPanelClose(event: PanelCloseEvent) {
		val panel = event.panel

		if (!panel.panelFlags.contains(NO_CLOSE)) {
			event.panel.onCloseEvent(event)
		} else
			event.player.openInventory(event.inventory)
	}

}