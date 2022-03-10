package de.jet.paper.tool.display.item.action

import de.jet.jvm.extension.container.addIfNotContained
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache
import org.bukkit.event.Event

sealed interface ItemAction<T : Event> : Identifiable<ItemAction<T>> {

    val registrationTag: ItemActionTag
        get() = ItemActionTag(identity)

    val executionProcess: suspend T.() -> Unit

    val type: ItemActionType

    fun register() = JetCache.itemActions.addIfNotContained(this)

    fun unregister() = JetCache.itemActions.remove(this)

    fun isRegistered() = JetCache.itemActions.contains(this)

}