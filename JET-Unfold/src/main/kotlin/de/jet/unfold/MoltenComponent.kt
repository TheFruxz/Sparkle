package de.jet.unfold

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import java.util.function.Consumer

object MoltenComponent {

	@JvmStatic
	fun construct(process: Consumer<TextComponent.Builder>): Component = text(process::accept)

	@JvmStatic
	fun fromString(text: String) = text {
		text(text)
	}

}