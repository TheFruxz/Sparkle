package de.jet.paper.app.component.buildMode

import de.jet.paper.app.component.buildMode.config.BuildModeConfiguration
import de.jet.paper.app.component.buildMode.config.BuildModeConfiguration.ProtectedAction.*
import de.jet.paper.app.component.buildMode.config.BuildModeManager
import de.jet.paper.extension.paper.buildMode
import de.jet.paper.extension.paper.isPhysical
import de.jet.paper.runtime.event.interact.PlayerInteractAtBlockEvent
import de.jet.paper.structure.app.event.EventListener
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event.Result.DENY
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action.LEFT_CLICK_BLOCK
import org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketEntityEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerDropItemEvent

internal class BuildModeListener : EventListener() {

	private fun Cancellable.buildModeCancel(entity: Entity, action: BuildModeConfiguration.ProtectedAction, affected: Material?) {
		val configState = BuildModeManager.state

		if (entity is Player && !entity.buildMode && configState.protectedActions.also { println("protected? ($it) -> ") }.contains(action.also { println("e: $it") }).also { println(it) } && !configState.ignoredMaterials.contains(affected?.name)) {
			this.isCancelled = true
		}
	}

	@EventHandler
	fun onBlockDestroy(event: BlockBreakEvent) =
		event.buildModeCancel(event.player, DESTROY, event.block.type)

	@EventHandler
	fun onBlockPlace(event: BlockPlaceEvent) =
		event.buildModeCancel(event.player, PLACE, event.blockPlaced.type)

	@EventHandler
	fun onBukkitEmpty(event: PlayerBucketEmptyEvent) =
		event.buildModeCancel(event.player, BUCKET, event.bucket)

	@EventHandler
	fun onBukkitEntity(event: PlayerBucketEntityEvent) =
		event.buildModeCancel(event.player, BUCKET, event.originalBucket.type)

	@EventHandler
	fun onBukkitFill(event: PlayerBucketFillEvent) =
		event.buildModeCancel(event.player, BUCKET, event.bucket)

	@EventHandler
	fun onItemPickUp(event: EntityPickupItemEvent) =
		event.buildModeCancel(event.entity, PICKUP_ITEM, event.item.itemStack.type)

	@EventHandler
	fun onItemDrop(event: PlayerDropItemEvent) =
		event.buildModeCancel(event.player, DROP_ITEM, event.itemDrop.itemStack.type)

	@EventHandler
	fun onBlockInteract(event: PlayerInteractAtBlockEvent) {
		if (!event.player.buildMode) {
			val configState = BuildModeManager.state

			if (event.action == RIGHT_CLICK_BLOCK && configState.protectedActions.contains(RIGHT_CLICK_INTERACT) || event.action == LEFT_CLICK_BLOCK && configState.protectedActions.contains(LEFT_CLICK_INTERACT) || event.action.isPhysical && configState.protectedActions.contains(PHYSICAL)) {
				event.interactedBlock = DENY
			}

		}
	}

}