package de.jet.library.tool.display.message

import de.jet.app.JetData
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.command.CommandSender

@Serializable
data class Transmission(
	var prefix: Component = Component.text(JetData.systemPrefix.content),
	// TODO: 24.07.2021 SoundMelody
	var content: TextComponent.Builder = Component.text(),
	val receivers: MutableList<CommandSender> = mutableListOf(),
	var withoutPrefix: Boolean = false,
) {
}