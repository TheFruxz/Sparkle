package de.moltenKt.paper.tool.display.ui.canvas

import net.kyori.adventure.key.Key
import net.kyori.adventure.text.TextComponent

data class MutableCanvas(
    override val key: Key,
    override var label: TextComponent,
    override val canvas: CanvasSize,
) : Canvas(key, label, ) {
}