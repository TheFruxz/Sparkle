package de.moltenKt.unfold.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent

fun Collection<ComponentLike>.joinToComponent(): TextComponent {
    val builder = Component.empty()
    forEach { builder.append(it) }
    return builder
}