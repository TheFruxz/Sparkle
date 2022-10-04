package de.fruxz.sparkle.framework.extension.visual

import de.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent.Builder

@Deprecated("Use the unfold buildComponent/text instead", ReplaceWith("text(base, process)", "de.fruxz.stacked.text"))
fun buildTextComponent(base: Builder = Component.text("").toBuilder(), process: Builder.() -> Unit) =
	text(base, process)