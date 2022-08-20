package de.moltenKt.unfold.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent

fun Collection<ComponentLike>.joinToComponent(): TextComponent {
    val builder = Component.empty()
    forEach { builder.append(it) }
    return builder
}

/**
 * This function splits every line of the component to its
 * own component, using [asStyledString] conversion, then
 * [split] and after that the back conversion via [asStyledComponent].
 * This is useful if you need the specific lines of the component,
 * like adding a prefix to each line.
 * @return A list of components, each representing a line of the original component
 * @author Fruxz
 * @since 1.0
 */
fun <T : ComponentLike> T.lines(): List<TextComponent> =
    asStyledString.split("<br>").asStyledComponents