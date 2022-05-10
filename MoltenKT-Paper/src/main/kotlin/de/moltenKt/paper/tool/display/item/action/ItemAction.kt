package de.moltenKt.paper.tool.display.item.action

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.core.tool.timing.calendar.Calendar
import org.bukkit.event.Event

sealed interface ItemAction<T : Event> : Identifiable<ItemAction<T>> {

    val registrationTag: ItemActionTag
        get() = ItemActionTag(identity)

    val executionProcess: suspend T.() -> Unit

    val type: ItemActionType

    val created: Calendar

    fun register()

    fun unregister()

    fun isRegistered(): Boolean

}