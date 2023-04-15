package dev.fruxz.sparkle.framework.event.interaction

import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

interface SparkleInteractEvent {

    val origin: PlayerInteractEvent

    var blockInteraction: Event.Result

    var itemInteraction: Event.Result

    companion object {

        fun <T : SparkleInteractEvent> T.denyInteraction() {
            this.blockInteraction = Event.Result.DENY
            this.itemInteraction = Event.Result.DENY
        }

        fun <T : SparkleInteractEvent> T.allowInteraction() {
            this.blockInteraction = Event.Result.ALLOW
            this.itemInteraction = Event.Result.ALLOW
        }

    }

}