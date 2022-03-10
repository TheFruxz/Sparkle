package de.jet.paper.tool.display.item.action

import de.jet.jvm.tool.smart.identification.Identifiable

data class ItemActionTag(
    override val identity: String,
) : Identifiable<ItemActionTag>
