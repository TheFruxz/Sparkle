package de.moltenKt.paper.tool.display.item.action

import de.moltenKt.core.tool.smart.identification.Identifiable

data class ItemActionTag(
    override val identity: String,
) : Identifiable<ItemActionTag>
