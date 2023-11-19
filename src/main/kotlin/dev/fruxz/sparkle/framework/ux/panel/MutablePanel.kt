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
    override var displayContext: CoroutineContext = sparkle.asyncDispatcher,
    override var updateContext: CoroutineContext = sparkle.asyncDispatcher,
    override var uuid: UUID = UUID.randomUUID(),
) : Panel(
    label = label,
    format = format,
    flags = flags,
    sound = sound,
    content = content,
    lazyContent = lazyContent,
    displayContext = displayContext,
    updateContext = updateContext,
) {

    public override val clickActions = MutableListMap<Int, ClickAction>()

    // set

    operator fun set(slot: Int, item: ItemLike) =
        content.set(slot, item)

    operator fun set(slot: Int, itemStack: ItemStack) =
        this.set(slot = slot, item = itemStack.itemLike)

    // set deferred

    fun deferred(slot: Int, item: Deferred<ItemLike>) =
        lazyContent.set(slot, item)

    fun deferred(slot: Int, builder: suspend () -> ItemLike) =
        lazyContent.set(slot, asAsync { builder() })

    operator fun set(slot: Int, item: Deferred<ItemLike>) =
        deferred(slot, item)

    operator fun set(slot: Int, builder: suspend () -> ItemLike) =
        deferred(slot, asAsync { builder() })

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

    fun toPanel() = Panel(
        label = label,
        format = format,
        flags = flags.toSet(),
        sound = sound,
        content = content.toMap(),
        lazyContent = lazyContent.toMap(),
        displayContext = displayContext,
        updateContext = updateContext,
    )

}
