package dev.fruxz.sparkle.server.component.events

import dev.fruxz.sparkle.framework.event.attack.PlayerDamageByPlayerEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class DamageListener : Listener {

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player || event.damager !is Player) return

        val result = PlayerDamageByPlayerEvent(
            victim = event.entity as Player,
            attacker = event.damager as Player,
            origin = event,
            isCancelled = event.isCancelled,
        )

        result.callEvent()

        event.isCancelled = result.isCancelled

    }

}