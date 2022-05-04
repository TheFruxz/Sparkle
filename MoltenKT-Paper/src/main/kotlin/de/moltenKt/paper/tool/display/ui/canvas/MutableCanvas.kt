package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class MutableCanvas(
    override val key: Key,
    override var label: TextComponent = Component.empty(),
    override val canvasSize: CanvasSize = CanvasSize.MEDIUM,
    override var content: Map<Int, ItemLike> = emptyMap(),
    override val panelFlags: Set<PanelFlag> = emptySet(),
    ) : Canvas(key, label, canvasSize, content, panelFlags) {

    operator fun set(slot: Int, itemLike: ItemLike?) {
        if (itemLike != null) {
            content += slot to itemLike
        } else
            content -= slot
    }

    operator fun set(slots: Iterable<Int>, itemLike: ItemLike?) =
        slots.forEach { set(it, itemLike) }

    operator fun set(vararg slots: Int, itemLike: ItemLike?) =
        set(slots.toList(), itemLike)

    // ItemStack support

    operator fun set(slot: Int, itemStack: ItemStack?) =
        set(slot, itemStack?.let { ItemLike.of(it) })

    operator fun set(slots: Iterable<Int>, itemStack: ItemStack?) =
        set(slots, itemStack?.let { ItemLike.of(it) })

    operator fun set(vararg slots: Int, itemStack: ItemStack?) =
        set(slots.toList(), itemStack?.let { ItemLike.of(it) })

    // Material support

    operator fun set(slot: Int, material: Material?) =
        set(slot, material?.let { ItemLike.of(it) })

    operator fun set(slots: Iterable<Int>, material: Material?) =
        set(slots, material?.let { ItemLike.of(it) })

    operator fun set(vararg slots: Int, material: Material?) =
        set(slots.toList(), material?.let { ItemLike.of(it) })

    // Design

    fun border(itemLike: ItemLike) =
        set(canvasSize.borderSlots, itemLike)

    fun fill(itemLike: ItemLike) =
        set(canvasSize.slots, itemLike)

    fun replace(replaceWith: ItemLike?, search: IndexedValue<ItemLike?>.() -> Boolean) =
        canvasSize.slots.forEach { slot ->
            if (search(IndexedValue(slot, this[slot]))) {
                set(slot, replaceWith)
            }
        }

    fun background(replaceWith: ItemLike?) =
        replace(replaceWith) { value?.asItemStack()?.type.let { it == null || it.isAir } }

}