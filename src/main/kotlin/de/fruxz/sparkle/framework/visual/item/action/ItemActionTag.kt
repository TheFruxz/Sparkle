package de.fruxz.sparkle.framework.visual.item.action

import dev.fruxz.ascend.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable

@Serializable
data class ItemActionTag(
    override val identity: String,
) : Identifiable<ItemActionTag>
