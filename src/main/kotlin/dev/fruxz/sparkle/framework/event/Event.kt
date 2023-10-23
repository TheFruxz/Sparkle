package dev.fruxz.sparkle.framework.event

import dev.fruxz.sparkle.framework.world.plus
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

val Action.isPhysical: Boolean
    get() = this == Action.PHYSICAL

val PlayerInteractEvent.possibleFutureBlock: Location?
    get() = clickedBlock?.let {
        when {
            it.isReplaceable -> it.location
            else -> it.location.toBlockLocation() + blockFace.direction
        }
    }

val <T : InventoryInteractEvent> T.player: Player
    get() = whoClicked as Player

// management

fun PluginManager.unregisterEvents() = HandlerList.unregisterAll()

fun PluginManager.unregisterEvents(plugin: Plugin) = HandlerList.unregisterAll(plugin)

fun PluginManager.unregisterEvents(listener: Listener) = HandlerList.unregisterAll(listener)