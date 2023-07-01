package dev.fruxz.sparkle.framework.ux.inventory.item

import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.ascend.tool.smart.composition.Composable
import dev.fruxz.sparkle.framework.coroutine.dispatcher.asyncDispatcher
import dev.fruxz.sparkle.framework.coroutine.scope.coroutineScope
import dev.fruxz.sparkle.framework.nbt.get
import dev.fruxz.sparkle.framework.nbt.persistentData
import dev.fruxz.sparkle.framework.nbt.set
import dev.fruxz.sparkle.framework.system.itemFactory
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.ux.inventory.item.action.ActionReaction
import dev.fruxz.sparkle.framework.ux.inventory.item.action.ActionTrigger
import dev.fruxz.sparkle.framework.ux.inventory.item.compose.ComposeAddon
import dev.fruxz.sparkle.framework.ux.inventory.item.compose.ComposeProperty
import dev.fruxz.stacked.extension.isNotEmpty
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.event.HoverEvent.ShowItem
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import java.util.function.UnaryOperator
import kotlin.coroutines.CoroutineContext

data class Item(
    var material: Material = Material.STONE,
    var label: ComponentLike = Component.translatable(material.translationKey()),
    var size: Int = 1,
    var lore: List<Component> = emptyList(),
    var damage: Int = 0,
    var enchantments: Set<EnchantmentData> = emptySet(),
    var flags: Set<ItemFlag> = emptySet(),
    var quirk: Quirk<out ItemMeta> = Quirk { },
    var identity: UUID = UUID.randomUUID(),
    var data: Map<Key, Any> = mapOf(),
    var itemMeta: ItemMeta = itemFactory.getItemMeta(material),
    var composeProperties: Set<ComposeProperty> = emptySet(),
    var composeAddons: Set<ComposeAddon> = emptySet(),
    var composeContext: Composable<CoroutineContext> = Composable { sparkle.asyncDispatcher }, // do to access to running sparkle instance
) : ItemLike, HoverEventSource<ShowItem>, Composable<Deferred<ItemStack>> {

    constructor(sourceMaterial: Material) : this(material = sourceMaterial)

    constructor(
        sourceItemStack: ItemStack,
        quirk: Quirk<ItemMeta> = Quirk { },
        postProperties: Set<ComposeProperty> = emptySet(),
        addons: Set<ComposeAddon> = emptySet(),
        composeContext: Composable<CoroutineContext> = Composable { sparkle.asyncDispatcher },
    ) : this(
        material = sourceItemStack.type,
        label = sourceItemStack.itemMeta?.displayName()
            ?: Component.translatable(sourceItemStack.type.translationKey()),
        size = sourceItemStack.amount,
        lore = sourceItemStack.itemMeta?.lore().orEmpty(),
        damage = sourceItemStack.itemMeta?.takeIfInstance<Damageable>()?.damage ?: 0,
        enchantments = sourceItemStack.enchantments.map { EnchantmentData(it.key, it.value) }.toSet(),
        flags = sourceItemStack.itemFlags,
        quirk = quirk,
        composeProperties = postProperties,
        identity = UUID.fromString(sourceItemStack.itemMeta.persistentDataContainer.get<String>(itemIdentityKey)),
        data = sourceItemStack.itemMeta?.persistentDataContainer?.persistentData.orEmpty(),
        itemMeta = sourceItemStack.itemMeta ?: sourceItemStack.type.itemMeta,
        composeAddons = addons,
        composeContext = composeContext,
    )

    val actions: Map<ActionTrigger, ActionReaction<out Event>>
        get() = globalItemActions[identity].orEmpty()

    private fun composeItemMeta() = when {
        material.isAir -> null
        else -> {
            val workMeta = itemMeta.clone()

            label.asComponent()
                .takeIf { it.isNotEmpty }
                ?.let {
                    workMeta.displayName(
                        Component.text().decoration(TextDecoration.ITALIC, false).append(it).build()
                    )
                }

            this.lore
                .takeIf { it.any { it.isNotEmpty } }
                ?.let { checkedLore ->
                    workMeta.lore(checkedLore.map {
                        Component.text().decoration(TextDecoration.ITALIC, false).append(it).build()
                    })
                }

            workMeta.addItemFlags(*flags.toTypedArray())

            workMeta
        }
    }

    override fun compose(): Deferred<ItemStack> = sparkle.coroutineScope.async(
        context = composeContext.compose(),
        start = CoroutineStart.UNDISPATCHED,
    ) {
        val itemMeta = composeItemMeta()
        var itemStack = ItemStack(material, size)

        when {
            itemMeta != null -> {

                // enchantments
                enchantments.forEach { enchantmentData ->
                    itemMeta.addEnchant(enchantmentData.enchantment, enchantmentData.level, true)
                }

                // damage
                if (itemMeta is Damageable) {
                    itemMeta.damage = damage
                }

                // compose properties
                if (ComposeProperty.NO_ENCHANTMENTS in composeProperties) {
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                }

                if (ComposeProperty.NO_IDENTITY !in composeProperties) {
                    itemMeta.persistentDataContainer[itemIdentityKey] = "$identity"
                }

                if (ComposeProperty.BLANK_LABEL in composeProperties) {
                    itemMeta.displayName(Component.text(" "))
                }

                if (ComposeProperty.NO_DATA !in composeProperties) {
                    itemMeta.persistentDataContainer.persistentData = this@Item.data
                }

                // item flags
                itemMeta.addItemFlags(*flags.toTypedArray())

                // finalization
                itemStack.itemMeta = itemMeta

            }
        }

        if (ComposeProperty.BLANK_DATA in composeProperties) {
            itemStack.addItemFlags(*ItemFlag.entries.toTypedArray())
        }

        itemStack = itemStack.apply { quirk.applyOnItemStack(this) }

        composeAddons.forEach { addon -> addon.plugin(itemStack) }

        return@async itemStack
    }

    override fun asItemStack(): ItemStack = runBlocking { compose().await() }

    override fun asItem(): Item = this

    override fun asHoverEvent(op: UnaryOperator<ShowItem>): HoverEvent<ShowItem> =
        produce().asHoverEvent()

    companion object {

        internal val itemIdentityKey = Key.key("sparkle", "item.identity")

        internal val globalItemActions = mutableMapOf<UUID, MutableMap<ActionTrigger, ActionReaction<out Event>>>()

    }

}