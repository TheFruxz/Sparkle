package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.sparkle.framework.coroutine.dispatcher.asyncDispatcher
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.ux.inventory.item.ItemLike
import kotlinx.coroutines.Deferred
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import java.util.*
import kotlin.coroutines.CoroutineContext

fun buildPanel(
    label: ComponentLike = Component.empty(),
    format: PanelFormat = PanelFormat.ofLines(PanelFormat.InventoryLines(3)),
    flags: MutableSet<PanelFlag> = mutableSetOf(),
    sound: PanelSound = PanelSound(onOpen = null, onClose = null),
    content: MutableMap<Int, ItemLike> = mutableMapOf(),
    lazyContent: MutableMap<Int, Deferred<ItemLike>> = mutableMapOf(),
    displayContext: CoroutineContext = sparkle.asyncDispatcher,
    updateContext: CoroutineContext = sparkle.asyncDispatcher,
    uuid: UUID = UUID.randomUUID(),
    process: MutablePanel.() -> Unit,
) : Panel =
    MutablePanel(
        label = label,
        format = format,
        flags = flags,
        sound = sound,
        content = content,
        lazyContent = lazyContent,
        displayContext = displayContext,
        updateContext = updateContext,
        uuid = uuid,
    ).apply(process).toPanel()