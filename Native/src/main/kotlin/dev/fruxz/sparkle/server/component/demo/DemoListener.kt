package dev.fruxz.sparkle.server.component.demo

import dev.fruxz.sparkle.framework.event.interaction.PlayerInteractAtItemEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class DemoListener : Listener {

    @EventHandler
    fun onBlockItemInteract(event: PlayerInteractAtItemEvent) {
        event.player.sendMessage("You interacted with a item!")
        event.denyItemInteraction()
    }

}