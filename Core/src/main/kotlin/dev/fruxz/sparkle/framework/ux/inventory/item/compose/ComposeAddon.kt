package dev.fruxz.sparkle.framework.ux.inventory.item.compose

import org.bukkit.inventory.ItemStack

fun interface ComposeAddon {
    suspend fun plugin(itemStack: ItemStack)
}