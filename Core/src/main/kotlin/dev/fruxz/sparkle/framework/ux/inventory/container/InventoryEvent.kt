package dev.fruxz.sparkle.framework.ux.inventory.container

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

@set:ApiStatus.Internal
var InventoryClickEvent.affectedItem: ItemStack?
    get() = currentItem ?: cursor
    set(value)  {
        @Suppress("DEPRECATION")
        when {
            currentItem != null -> currentItem = value
            else -> setCursor(value)
        }
    }