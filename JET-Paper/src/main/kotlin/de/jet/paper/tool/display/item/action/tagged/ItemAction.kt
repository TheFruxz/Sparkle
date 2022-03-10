package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.tool.smart.identification.Identifiable
import org.bukkit.event.Event

sealed interface ItemAction<T : Event> : Identifiable<ItemAction<T>> {

    fun execute(event: T) = executionProcess(event)

    val registrationTag: ItemActionTag
        get() = ItemActionTag(identity)

    val executionProcess: T.() -> Unit

    val type: ItemActionType

}