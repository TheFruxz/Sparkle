package de.jet.paper.app.component.linking

import de.jet.jvm.extension.container.firstOrNull
import de.jet.paper.app.JetCache
import de.jet.paper.runtime.event.PanelClickEvent
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.event.EventHandler

internal class PanelLinkListener : EventListener() {

	@EventHandler
	fun onPanelClick(event: PanelClickEvent) {
		JetCache.panelInteractions
			.firstOrNull { it.key.identity == event.panel.identity }?.value
			?.forEach { clickAction -> event.apply(clickAction) }
	}

}