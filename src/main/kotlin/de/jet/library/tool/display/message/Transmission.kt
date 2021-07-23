package de.jet.library.tool.display.message

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component

@Serializable
data class Transmission(
	val prefix: Component = Component.text("prefix")
) {
}