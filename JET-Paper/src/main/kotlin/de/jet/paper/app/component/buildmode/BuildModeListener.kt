package de.jet.paper.app.component.buildmode

import de.jet.paper.extension.paper.buildMode
import de.jet.paper.extension.system
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.tool.annotation.RequiresComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

internal class BuildModeListener : EventListener {

	override val vendor = system

	@EventHandler
	@OptIn(RequiresComponent::class)
	fun onBlockDestroy(event: BlockBreakEvent) {
		if (!event.player.buildMode) {
			event.isCancelled = true
		}
	}

	@EventHandler
	@OptIn(RequiresComponent::class)
	fun onBlockPlace(event: BlockPlaceEvent) {
		if (!event.player.buildMode) {
			event.isCancelled = true
		}
	}

	@EventHandler
	@OptIn(RequiresComponent::class)
	fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
		if (!event.player.buildMode) {
			event.isCancelled = true
		}
	}

}