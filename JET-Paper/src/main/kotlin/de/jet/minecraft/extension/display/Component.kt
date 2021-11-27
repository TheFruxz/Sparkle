package de.jet.minecraft.extension.display

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent.Builder

fun buildTextComponent(base: Builder = Component.text("").toBuilder(), process: Builder.() -> Unit) =
	base.apply(process).build()