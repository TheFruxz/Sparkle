package de.jet.app.component.item

import de.jet.library.extension.display.ui.item
import de.jet.library.extension.mainLog
import de.jet.library.extension.tasky.task
import de.jet.library.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.tasky.TemporalAdvice
import de.jet.library.tool.tasky.TemporalAdvice.Companion
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.logging.Level

class JetActionComponent(vendor: App) : Component(vendor) {

	override val id = "JETActions"

	private val handler = Handler(vendor)

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	private class Handler(override val vendor: App) : EventListener, Listener {

		@EventHandler
		fun onInventoryClick(event: InventoryClickEvent) {
			event.currentItem?.item?.clickAction?.let { action ->
				if (!action.stop)
					event.isCancelled = true
				task(vendor, TemporalAdvice.instant(async = action.async)) {
					action.action(event)
				}
			}
		}

		@EventHandler
		fun playerInteractAtItem(event: PlayerInteractAtItemEvent) {
			with(event) {
				item.interactAction?.let { action ->
					if (!action.stop)
						event.isCancelled = true
					task(vendor, TemporalAdvice.instant(async = action.async)) {
						action.action(event)
					}
				}
			}
		}

	}

}