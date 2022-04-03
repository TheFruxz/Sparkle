package de.jet.paper.app.component.buildMode

import de.jet.paper.extension.paper.buildMode
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketEntityEvent
import org.bukkit.event.player.PlayerBucketFillEvent

internal class BuildModeListener : EventListener() {

	private fun Cancellable.buildModeCancel(entity: Entity) {
		if (entity is Player && !entity.buildMode) {
			this.isCancelled = true
		}
	}

	@EventHandler
	fun onBlockDestroy(event: BlockBreakEvent) =
		event.buildModeCancel(event.player)

	@EventHandler
	fun onBlockPlace(event: BlockPlaceEvent) =
		event.buildModeCancel(event.player)

	@EventHandler
	fun onBukkitEmpty(event: PlayerBucketEmptyEvent) =
		event.buildModeCancel(event.player)

	@EventHandler
	fun onBukkitEntity(event: PlayerBucketEntityEvent) =
		event.buildModeCancel(event.player)

	@EventHandler
	fun onBukkitFill(event: PlayerBucketFillEvent) =
		event.buildModeCancel(event.player)

	@EventHandler
	fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
		if (!event.player.buildMode) {
			event.interactedBlock = DENY
		}
	}

}