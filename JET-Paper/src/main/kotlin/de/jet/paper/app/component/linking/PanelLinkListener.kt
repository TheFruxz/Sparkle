package de.jet.paper.app.component.linking

import de.jet.paper.app.JetCache
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.async
import de.jet.paper.runtime.event.PanelClickEvent
import de.jet.paper.structure.app.event.EventListener
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler

internal class PanelLinkListener : EventListener() {

	@EventHandler
	fun onPanelClick(event: PanelClickEvent) = with(event) { async {
			JetCache.panelInteractions.filter { it.key.identity == panel.identity }.forEach { (_, value) ->
				value[event.clickedSlot]?.forEach { process ->
					system.coroutineScope.launch { process(this@with) }
				}
			}
		}
	}

}