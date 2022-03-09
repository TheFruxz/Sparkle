package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.tool.smart.identification.Identifiable
import org.bukkit.event.Event

data class ItemActionTag<T : Event>(
    override val identity: String,
) : Identifiable<ItemAction<T>>
