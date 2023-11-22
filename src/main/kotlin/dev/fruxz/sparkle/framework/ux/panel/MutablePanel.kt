package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.ascend.tool.map.list.MutableListMap
import dev.fruxz.sparkle.framework.coroutine.dispatcher.asyncDispatcher
import dev.fruxz.sparkle.framework.coroutine.task.asAsync
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.ux.inventory.item.ItemLike
import dev.fruxz.sparkle.framework.ux.inventory.item.itemLike
import kotlinx.coroutines.Deferred
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.coroutines.CoroutineContext

data class MutablePanel(
    override var label: ComponentLike = Component.empty(),
    override var format: PanelFormat = PanelFormat.ofLines(PanelFormat.InventoryLines(3)),
    override val flags: MutableSet<PanelFlag> = mutableSetOf(),
    override var sound: PanelSound = PanelSound(onOpen = null, onClose = null),
    override val content: MutableMap<Int, ItemLike> = mutableMapOf(),
    override val lazyContent: MutableMap<Int, Deferred<ItemLike>> = mutableMapOf(),
    override val lazyContext: CoroutineContext = sparkle.asyncDispatcher,
    override var uuid: UUID = UUID.randomUUID(),
) : Panel(
    label = label,
    format = format,
    flags = flags,
    sound = sound,
    content = content,
    lazyContent = lazyContent,
) {

    public override val clickActions = MutableListMap<Int, ClickAction>()
    override val compileAddIns = MutableListMap<CompilePhase, (Inventory, Panel) -> Unit>()

    // set

    operator fun set(slot: Int, item: ItemLike) =
        content.set(slot, item)

    operator fun set(slot: Int, itemStack: ItemStack) =
        this.set(slot = slot, item = itemStack.itemLike)

    operator fun set(slots: Iterable<Int>, item: ItemLike) =
        slots.forEach { this[it] = item }

    operator fun set(slots: Iterable<Int>, itemStack: ItemStack) =
        slots.forEach { this[it] = itemStack.itemLike }

    // set deferred

    fun deferred(slot: Int, item: Deferred<ItemLike>) =
        lazyContent.set(slot, item)

    fun deferred(slot: Int, builder: suspend () -> ItemLike) =
        lazyContent.set(slot, asAsync(context = lazyContext) { builder() })

    fun deferred(slots: Iterable<Int>, item: Deferred<ItemLike>) =
        slots.forEach { deferred(it, item) }

    fun deferred(slots: Iterable<Int>, builder: suspend () -> ItemLike) =
        slots.forEach { deferred(it, builder) }

    operator fun set(slot: Int, item: Deferred<ItemLike>) =
        deferred(slot, item)

    operator fun set(slot: Int, builder: suspend () -> ItemLike) =
        deferred(slot, asAsync(context = lazyContext) { builder() })

    operator fun set(slots: Iterable<Int>, item: Deferred<ItemLike>) =
        deferred(slots, item)

    operator fun set(slots: Iterable<Int>, builder: suspend () -> ItemLike) =
        deferred(slots, builder)

    // click actions

    @SparkleDSL
    fun onClick(slot: Int, action: ClickAction) =
        clickActions.addEntry(slot, action)

    @SparkleDSL
    fun onClick(slot: Int, process: suspend (InventoryClickEvent) -> Unit) =
        onClick(slot, ClickAction(process))

    @SparkleDSL
    operator fun set(slot: Int, action: ClickAction) =
        onClick(slot, action)

    @SparkleDSL
    fun onClick(slots: Iterable<Int>, action: ClickAction) =
        slots.forEach { onClick(it, action) }

    @SparkleDSL
    fun onClick(slots: Iterable<Int>, process: suspend (InventoryClickEvent) -> Unit) =
        slots.forEach { onClick(it, process) }

    @SparkleDSL
    operator fun set(slots: Iterable<Int>, action: ClickAction) =
        onClick(slots, action)

    // compile add-ins

    fun onCompile(phase: CompilePhase, action: (Inventory, Panel) -> Unit) =
        compileAddIns.addEntry(phase, action)

    fun toPanel(): Panel = Panel(
        label = label,
        format = format,
        flags = flags.toMutableSet(),
        sound = sound,
        content = content.toMutableMap(),
        lazyContent = lazyContent.toMutableMap(),
        lazyContext = lazyContext,
        uuid = uuid,
    ).apply {
        this.clickActions.putAll(this@MutablePanel.clickActions)
        this.compileAddIns.putAll(this@MutablePanel.compileAddIns)
    }

}
