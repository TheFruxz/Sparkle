package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.ascend.extension.forceCastOrNull
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun interface Quirk<T : ItemMeta> {

    suspend fun applyQuirk(itemMeta: T)

    suspend fun applyOnItemStack(itemStack: ItemStack) {
        val itemMeta = itemStack.itemMeta.forceCastOrNull<T>()

        when (itemMeta) {
            is T -> applyQuirk(itemMeta)
            else -> return
        }

        itemStack.itemMeta = itemMeta
    }

}