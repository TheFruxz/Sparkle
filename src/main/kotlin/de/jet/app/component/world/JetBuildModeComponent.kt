package de.jet.app.component.world

import de.jet.library.extension.paper.buildMode
import de.jet.library.extension.system
import de.jet.library.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.library.structure.app.App
import de.jet.library.structure.app.event.EventListener
import de.jet.library.structure.component.Component
import de.jet.library.structure.component.Component.RunType.AUTOSTART_MUTABLE
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class JetBuildModeComponent(vendor: App = system) : Component(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "BuildMode"

	private val handler = BuildModeHandler(vendor)

	override fun start() {
		vendor.add(handler)
	}

	override fun stop() {
		vendor.remove(handler)
	}

	class BuildModeHandler(override val vendor: App) : EventListener {

		@EventHandler
		fun onBlockDestroy(event: BlockBreakEvent) {
			if (!event.player.buildMode) {
				event.isCancelled = true
			}
		}

		@EventHandler
		fun onBlockPlace(event: BlockPlaceEvent) {
			if (!event.player.buildMode) {
				event.isCancelled = true
			}
		}

		@EventHandler
		fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
			if (!event.player.buildMode) {
				event.isCancelled = true
			}
		}

	}

}