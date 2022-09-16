package de.moltenKt.unfold.extension

import de.moltenKt.unfold.Unfold
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

@Unfold fun TextComponent.Builder.newlines(amount: Int) = apply { repeat(amount) { append(Component.newline()) } }

@Unfold fun TextComponent.Builder.space() = append(Component.space())

@Unfold fun TextComponent.Builder.spaces(amount: Int) = apply { repeat(amount) { space() } }
