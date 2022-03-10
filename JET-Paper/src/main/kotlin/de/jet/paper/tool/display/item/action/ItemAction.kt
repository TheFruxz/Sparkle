package de.jet.paper.tool.display.item.action

import de.jet.jvm.tool.smart.identification.Identifiable
import org.bukkit.event.Event

sealed interface ItemAction<T : Event> : Identifiable<ItemAction<T>> {

    val registrationTag: ItemActionTag
        get() = ItemActionTag(identity)

    val executionProcess: suspend T.() -> Unit

    val type: ItemActionType

    fun register()

    fun unregister()

    fun isRegistered(): Boolean

}