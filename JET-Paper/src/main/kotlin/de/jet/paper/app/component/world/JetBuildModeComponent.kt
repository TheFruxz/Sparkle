package de.jet.paper.app.component.world

import de.jet.paper.app.interchange.player.BuildModeInterchange
import de.jet.paper.extension.paper.buildMode
import de.jet.paper.extension.system
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.structure.component.Component.RunType.DISABLED
import de.jet.paper.structure.component.SmartComponent
import de.jet.paper.tool.annotation.RequiresComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class JetBuildModeComponent(vendor: App = system) : SmartComponent(vendor, DISABLED) {

	override val thisIdentity = "BuildMode"

	override fun component() {
		listener(BuildModeListener(vendor))
		interchange(BuildModeInterchange(vendor))
	}

	class BuildModeListener(override val vendor: App) : EventListener {

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

		@OptIn(RequiresComponent::class)
		@EventHandler
		fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
			if (!event.player.buildMode) {
				event.isCancelled = true
			}
		}

	}

}