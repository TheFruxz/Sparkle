@file:JvmName("ItemKt")

package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.ascend.tool.smart.generate.composable.Composable
import dev.fruxz.sparkle.framework.ux.inventory.item.compose.ComposeAddon
import dev.fruxz.sparkle.framework.ux.inventory.item.compose.ComposeProperty
import dev.fruxz.stacked.extension.api.StyledString
import dev.fruxz.stacked.extension.asStyledComponents
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta
import org.jetbrains.annotations.ApiStatus
import java.util.*
import kotlin.coroutines.CoroutineContext

// material

fun Item.material(material: Material) = apply {
    this.material = material
}

// label

fun Item.label(label: ComponentLike) = apply {
    this.label = label
}

// size / amount

fun Item.size(amount: Int) = apply {
    this.size = amount
}

// lore

@JvmName("loreComponents")
fun Item.lore(lore: List<Component>) = apply {
    this.lore = lore
}

@JvmName("loreStyledStrings")
fun Item.lore(@StyledString stringList: List<String>) = apply {
    this.lore = stringList.asStyledComponents
}

@JvmName("loreStyledString")
fun Item.lore(@StyledString string: String) = apply {
    this.lore = string.asStyledComponents
}

// damage

fun Item.damage(damage: Int) = apply {
    this.damage = damage
}

// enchantments

fun Item.enchant(data: EnchantmentData) = apply {
    this.enchantments += data
}

fun Item.enchant(type: Enchantment, level: Int) = apply {
    this.enchantments += EnchantmentData(type, level)
}

fun Item.enchants(vararg data: EnchantmentData) = apply {
    this.enchantments += data
}

// flags

fun Item.flag(flag: ItemFlag) = apply {
    this.flags += flag
}

fun Item.flags(vararg flag: ItemFlag) = apply {
    this.flags += flag
}

// quirk

fun Item.quirk(quirk: Quirk<*>) = apply {
    this.quirk = quirk
}

fun <T : ItemMeta> Item.quirk(quirk: suspend (itemMeta: T) -> Unit) = apply {
    this.quirk = Quirk(quirk)
}

// identity

@ApiStatus.Internal
fun Item.identity(identity: UUID) = apply {
    this.identity = identity
}

// data

operator fun Item.set(key: Key, nbtValue: Any) = apply {
    data += key to nbtValue
}

operator fun <T> Item.get(key: Key) = apply {
    data[key].forceCast<T?>()
}

// itemMeta

fun Item.itemMeta(baseItemMeta: ItemMeta) = apply {
    this.itemMeta = baseItemMeta
}

// composeProperty

fun Item.composeProperty(composeProperty: ComposeProperty) = apply {
    this.composeProperties += composeProperty
}

fun Item.composeProperties(vararg composeProperty: ComposeProperty) = apply {
    this.composeProperties += composeProperty
}

// composeAddons

fun Item.composeAddon(composeAddon: ComposeAddon) = apply {
    this.composeAddons += composeAddon
}

fun Item.composeAddons(vararg composeAddon: ComposeAddon) = apply {
    this.composeAddons += composeAddon
}

// composeContext

fun Item.composeContext(composableContext: Composable<CoroutineContext>) = apply {
    this.composeContext = composableContext
}

fun Item.composeContext(context: CoroutineContext) = apply {
    this.composeContext = Composable { context }
}