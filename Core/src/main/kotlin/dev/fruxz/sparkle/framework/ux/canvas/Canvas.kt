package dev.fruxz.sparkle.framework.ux.canvas

import dev.fruxz.sparkle.framework.coroutine.dispatcher.asyncDispatcher
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.ux.canvas.format.CanvasFormat
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundEffect
import dev.fruxz.sparkle.framework.ux.inventory.item.ItemLike
import kotlinx.coroutines.Deferred
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import kotlin.coroutines.CoroutineContext

open class Canvas(
    val label: ComponentLike = Component.empty(),
    val format: CanvasFormat = CanvasFormat.ofLines(3),
    // pagination
    val content: Map<Int, ItemLike> = emptyMap(),
    val flags: Set<CanvasFlag> = emptySet(),
    val openingSound: SoundEffect? = null,
    val closingSound: SoundEffect? = null,
    val lazyItems: Map<Int, Deferred<ItemLike>> = emptyMap(),
    val displayContext: CoroutineContext = sparkle.asyncDispatcher,
    val updateContext: CoroutineContext = sparkle.asyncDispatcher,
) {

}