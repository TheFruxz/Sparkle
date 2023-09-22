package dev.fruxz.sparkle.framework.event.interaction

import dev.fruxz.sparkle.framework.event.SpecializedEvent
import org.bukkit.event.Event
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

interface SparkleInteractEvent<T : PlayerInteractEvent> : SpecializedEvent<T> {

    override val origin: T

    val action: Action

    var blockInteraction: Event.Result

    var itemInteraction: Event.Result

    fun denyInteraction() {
        this.blockInteraction = Event.Result.DENY
        this.itemInteraction = Event.Result.DENY
    }

    fun allowInteraction() {
        this.blockInteraction = Event.Result.ALLOW
        this.itemInteraction = Event.Result.ALLOW
    }

}