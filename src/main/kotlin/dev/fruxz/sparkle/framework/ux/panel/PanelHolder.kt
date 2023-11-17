package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.ux.inventory.container.set
import dev.fruxz.stacked.extension.asPlainString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

data class PanelHolder(
    val panel: Panel,
) : InventoryHolder {
    lateinit var inventoryState: Inventory

    override fun getInventory(): Inventory {
        if (!::inventoryState.isInitialized) inventoryState = panel.produce()

        return inventoryState
    }

    fun refresh() {
        if (PanelFlag.NO_REFRESH in panel.flags) return

        val current = getInventory()
        val new = panel.produce()

        debugLog { "Triggering refresh of panel ${panel.identifier()} for player ${current.viewers.joinToString { it.name().asPlainString }}" }

        when {
            PanelFlag.HARD_REFRESH in panel.flags -> current.viewers.forEach { it.openInventory(new) }
            else -> {
                new.contents.forEachIndexed { index, item ->
                    if (item != current.getItem(index)) current.setItem(index, item)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                panel.lazyContent.forEach { (index, item) ->
                    item.invokeOnCompletion { new[index] = item.getCompleted() }
                }
            }
        }

    }

}
