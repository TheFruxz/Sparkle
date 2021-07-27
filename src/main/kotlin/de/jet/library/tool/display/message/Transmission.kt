package de.jet.library.tool.display.message

import de.jet.app.JetData
import de.jet.library.extension.paper.consoleSender
import de.jet.library.extension.paper.onlinePlayers
import de.jet.library.tool.display.message.DisplayType.*
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.title.Title
import org.bukkit.command.CommandSender

@Serializable
data class Transmission(
	var prefix: Component = Component.text(JetData.systemPrefix.content),
	// TODO: 24.07.2021 SoundMelody
	var content: TextComponent.Builder = Component.text(),
	var participants: MutableList<CommandSender> = mutableListOf(),
	var withoutPrefix: Boolean = false,
	var displayType: DisplayType = DISPLAY_CHAT,
) {

	infix fun edit(action: Transmission.() -> Unit) = apply(action)

	infix fun prefix(prefix: Component) = edit { this.prefix = prefix }

	infix fun content(content: TextComponent.Builder) = edit { this.content = content }

	infix fun participants(participants: Collection<CommandSender>) = edit { this.participants = participants.toMutableList() }

	infix fun participants(participants: Array<CommandSender>) = participants(participants.toList())

	infix fun withoutPrefix(withoutPrefix: Boolean) = edit { this.withoutPrefix = withoutPrefix }

	infix fun displayType(displayType: DisplayType) = edit { this.displayType = displayType }

	fun display(): Transmission {
		participants
			.forEach { participant ->

				when (displayType) {
					DISPLAY_CHAT -> participant.sendMessage(content)
					DISPLAY_ACTIONBAR -> participant.sendActionBar(content)
					DISPLAY_TITLE -> participant.showTitle(Title.title(content.build(), Component.empty()))
					DISPLAY_SUBTITLE -> participant.showTitle(Title.title(Component.empty(), content.build()))
				}

				// TODO: 26.07.2021 Here SoundMelody-Sound

			}

		return this
	}

	fun broadcast() = this
		.copy()
		.participants(onlinePlayers + consoleSender)
		.display()

	override fun toString() = content.content()

}