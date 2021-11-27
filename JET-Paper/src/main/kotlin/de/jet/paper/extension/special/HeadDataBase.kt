package de.jet.paper.extension.special

import de.jet.paper.extension.display.ui.item
import de.jet.paper.tool.display.item.Item
import me.arcaniax.hdb.api.HeadDatabaseAPI

/**
 * This calls the HeadDatabase Plugin, which is also required!
 */
fun hdbSkull(id: Any?): Item = HeadDatabaseAPI().getItemHead("$id").let {
	return@let it.item.putBase(it.itemMeta)
}
