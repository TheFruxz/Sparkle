package dev.fruxz.sparkle.framework.event

import org.bukkit.event.Event

interface SpecializedEvent<T : Event> {

    val origin: T

}