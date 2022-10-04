package de.fruxz.sparkle.framework.visual.item.action

import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.timing.calendar.Calendar
import org.bukkit.event.Event

sealed interface ItemAction<T : Event> : Identifiable<ItemAction<T>> {

    val registrationTag: ItemActionTag
        get() = ItemActionTag(identity)

    val executionProcess: T.() -> Unit

    val type: ItemActionType

    val created: Calendar

    fun register()

    fun unregister()

    fun isRegistered(): Boolean

}