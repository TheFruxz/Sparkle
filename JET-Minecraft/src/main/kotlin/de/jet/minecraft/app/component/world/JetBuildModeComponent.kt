package de.jet.minecraft.app.component.world

import de.jet.minecraft.app.interchange.player.BuildModeInterchange
import de.jet.minecraft.extension.paper.buildMode
import de.jet.minecraft.extension.system
import de.jet.minecraft.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.minecraft.structure.component.SmartComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class JetBuildModeComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

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

		@EventHandler
		fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
			if (!event.player.buildMode) {
				event.isCancelled = true
			}
		}

	}

}