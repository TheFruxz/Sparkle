package de.moltenKt.paper.extension.display

import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent.Builder

@Deprecated("Use the unfold buildComponent/text instead", ReplaceWith("text(base, process)", "de.moltenKt.unfold.text"))
fun buildTextComponent(base: Builder = Component.text("").toBuilder(), process: Builder.() -> Unit) =
	text(base, process)