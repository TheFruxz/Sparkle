package de.jet.minecraft.tool.display.message

import de.jet.library.extension.paper.consoleSender
import de.jet.library.extension.paper.onlinePlayers
import de.jet.minecraft.app.JetData
import de.jet.minecraft.tool.display.message.DisplayType.*
import de.jet.minecraft.tool.effect.sound.SoundLibrary
import de.jet.minecraft.tool.effect.sound.SoundMelody
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.title.Title
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity

data class Transmission(
	var prefix: Component = Component.text(JetData.systemPrefix.content),
	var content: TextComponent.Builder = Component.text(),
	var participants: MutableList<CommandSender> = mutableListOf(),
	var withoutPrefix: Boolean = false,
	var displayType: DisplayType = DISPLAY_CHAT,
	var promptSound: SoundMelody? = null,
	var level: Level = Level.GENERAL,
	var hoverEvent: HoverEventSource<*>? = null,
	var clickEvent: ClickEvent? = null,
) {

	infix fun edit(action: Transmission.() -> Unit) = apply(action)

	infix fun prefix(prefix: Component) = edit { this.prefix = prefix }

	infix fun content(content: TextComponent.Builder) = edit { this.content = content }

	infix fun participants(participants: Collection<CommandSender>) = edit { this.participants = participants.toMutableList() }

	infix fun participants(participants: Array<CommandSender>) = participants(participants.toList())

	infix fun withoutPrefix(withoutPrefix: Boolean) = edit { this.withoutPrefix = withoutPrefix }

	infix fun displayType(displayType: DisplayType) = edit { this.displayType = displayType }

	infix fun promptSound(soundMelody: SoundMelody?) = edit { this.promptSound = soundMelody }

	infix fun hoverEvent(hoverEvent: HoverEventSource<*>?) = edit { this.hoverEvent = hoverEvent }

	infix fun hover(hover: HoverEventSource<*>?) = hoverEvent(hover)

	infix fun clickEvent(clickEvent: ClickEvent?) = edit { this.clickEvent = clickEvent }

	infix fun click(click: ClickEvent?) = clickEvent(click)

	fun display(): Transmission {
		val nextRound = mutableSetOf<Entity>()
		val displayObject = prefix
			.append(content).apply {
				hoverEvent?.let { hoverEvent(it) }
				clickEvent?.let { clickEvent(it) }
			}

		for (participant in participants) {

			when (displayType) {
				DISPLAY_CHAT -> participant.sendMessage(displayObject)
				DISPLAY_ACTIONBAR -> participant.sendActionBar(displayObject)
				DISPLAY_TITLE -> participant.showTitle(Title.title(displayObject, Component.empty()))
				DISPLAY_SUBTITLE -> participant.showTitle(Title.title(Component.empty(), displayObject))
			}

			if (participant is Entity)
				nextRound.add(participant)
		}

		promptSound?.play(nextRound)

		return this
	}

	fun broadcast() = this
		.copy()
		.participants(onlinePlayers + consoleSender)
		.display()

	override fun toString() = content.content()

	enum class Level(
		val promptSound: SoundMelody?,
	) {

		GENERAL(SoundLibrary.NOTIFICATION_GENERAL),
		INFO(SoundLibrary.NOTIFICATION_INFO),
		FAIL(SoundLibrary.NOTIFICATION_FAIL),
		ERROR(SoundLibrary.NOTIFICATION_ERROR),
		LEVEL(SoundLibrary.NOTIFICATION_LEVEL),
		WARNING(SoundLibrary.NOTIFICATION_WARNING),
		ATTENTION(SoundLibrary.NOTIFICATION_ATTENTION),
		PAYMENT(SoundLibrary.NOTIFICATION_PAYMENT),
		APPLIED(SoundLibrary.NOTIFICATION_APPLIED),

	}

}