package de.fruxz.sparkle.tool.display.item.action

import de.fruxz.ascend.tool.smart.identification.Identifiable

data class ItemActionTag(
    override val identity: String,
) : Identifiable<ItemActionTag>
