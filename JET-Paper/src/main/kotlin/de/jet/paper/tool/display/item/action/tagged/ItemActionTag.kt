package de.jet.paper.tool.display.item.action.tagged

import de.jet.jvm.tool.smart.identification.Identifiable
import org.bukkit.event.Event

data class ItemActionTag(
    override val identity: String,
) : Identifiable<ItemActionTag>
