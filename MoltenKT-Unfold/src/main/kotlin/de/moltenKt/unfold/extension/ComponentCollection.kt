package de.moltenKt.unfold.extension

import de.moltenKt.core.extension.container.splitBy
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentIteratorType
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent

fun Collection<ComponentLike>.joinToComponent(): TextComponent =
    Component.text().append(this).build()

/**
 * This function splits every line of the component to its
 * own component.
 * This is useful if you need the specific lines of the component,
 * like adding a prefix to each line.
 * @return A list of components, each representing a line of the original component
 * @author Fruxz
 * @since 1.0
 */
fun <T : ComponentLike> T.lines(): List<Component> =
    (asComponent().children().takeIf { it.isNotEmpty() } ?: listOf(asComponent()))
        .splitBy { it == Component.newline() }
        .map { it.joinToComponent() }
