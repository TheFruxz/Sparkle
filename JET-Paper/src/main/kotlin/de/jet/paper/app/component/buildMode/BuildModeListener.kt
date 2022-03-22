package de.jet.paper.app.component.buildMode

import de.jet.paper.extension.paper.buildMode
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

internal class BuildModeListener : EventListener() {

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
			event.interactedBlock = DENY
		}
	}

}