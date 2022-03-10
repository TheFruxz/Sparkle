package de.jet.paper.app.component.linking

import de.jet.jvm.extension.container.firstOrNull
import de.jet.paper.app.JetCache
import de.jet.paper.extension.app
import de.jet.paper.runtime.event.PanelClickEvent
import de.jet.paper.structure.app.event.EventListener
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler

internal class PanelLinkListener : EventListener() {

	@EventHandler
	fun onPanelClick(event: PanelClickEvent) {
		with(event) {
			JetCache.panelInteractions.firstOrNull { it.key.identity.also { println("||| first: $it") } == event.panel.identity.also { println("second: $it") } }?.also {
				println("--- found: $it")
			}?.value?.forEach { clickAction ->
				app(event.panel.vendor).coroutineScope.launch {
					clickAction(this@with)
				}
			}
		}
	}

}