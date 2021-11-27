package de.jet.minecraft.extension.special

import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.tool.display.item.Item
import me.arcaniax.hdb.api.HeadDatabaseAPI

/**
 * This calls the HeadDatabase Plugin, which is also required!
 */
fun hdbSkull(id: Any?): Item = HeadDatabaseAPI().getItemHead("$id").let {
	return@let it.item.putBase(it.itemMeta)
}
