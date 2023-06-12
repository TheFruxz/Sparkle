package dev.fruxz.sparkle.server.component.events

import dev.fruxz.sparkle.framework.event.interaction.PlayerInteractAtBlockEvent
import dev.fruxz.sparkle.framework.event.interaction.PlayerInteractAtItemEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class InteractionListener : Listener {

    @EventHandler
    fun playerInteract(event: PlayerInteractEvent) {

        if (event.hasBlock()) {
            val result = PlayerInteractAtBlockEvent(
                whoInteract = event.player,
                block = event.clickedBlock!!,
                material = event.clickedBlock!!.type,
                action = event.action,
                origin = event,
                blockInteraction = event.useInteractedBlock(),
                itemInteraction = event.useItemInHand(),
            )

            result.callEvent()

            event.setUseInteractedBlock(result.blockInteraction)
            event.setUseItemInHand(result.itemInteraction)
            event.isCancelled = result.isCancelled

        }

        if (event.hasItem()) {
            val result = PlayerInteractAtItemEvent(
                whoInteract = event.player,
                item = event.item!!,
                material = event.item!!.type,
                action = event.action,
                origin = event,
                blockInteraction = event.useInteractedBlock(),
                itemInteraction = event.useItemInHand(),
            )

            result.callEvent()

            event.setUseInteractedBlock(result.blockInteraction)
            event.setUseItemInHand(result.itemInteraction)
            event.isCancelled = result.isCancelled
        }


    }

}