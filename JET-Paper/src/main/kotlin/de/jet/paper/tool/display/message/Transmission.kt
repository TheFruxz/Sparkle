package de.jet.paper.tool.display.message

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tool.smart.positioning.Address.Companion.address
import de.jet.paper.app.JetData
import de.jet.paper.extension.lang
import de.jet.paper.extension.paper.adventureComponent
import de.jet.paper.extension.paper.consoleSender
import de.jet.paper.extension.paper.onlinePlayers
import de.jet.paper.tool.display.message.DisplayType.*
import de.jet.paper.tool.effect.sound.SoundLibrary
import de.jet.paper.tool.effect.sound.SoundMelody
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
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
	var prefixByLevel: Boolean = true,
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

		hoverEvent?.let { content.hoverEvent(it) }
		clickEvent?.let { content.clickEvent(it) }

		val displayObject = (if (prefixByLevel) lang("system.${level.prefixLink.address}").adventureComponent else prefix)
			.append(content)

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
		val prefixLink: Address<Level>,
	) {

		GENERAL(SoundLibrary.NOTIFICATION_GENERAL, address("prefix.general")),
		INFO(SoundLibrary.NOTIFICATION_INFO, address("prefix.info")),
		FAIL(SoundLibrary.NOTIFICATION_FAIL, address("prefix.fail")),
		ERROR(SoundLibrary.NOTIFICATION_ERROR, address("prefix.error")),
		LEVEL(SoundLibrary.NOTIFICATION_LEVEL, address("prefix.level")),
		WARNING(SoundLibrary.NOTIFICATION_WARNING, address("prefix.warning")),
		ATTENTION(SoundLibrary.NOTIFICATION_ATTENTION, address("prefix.attention")),
		PAYMENT(SoundLibrary.NOTIFICATION_PAYMENT, address("prefix.payment")),
		APPLIED(SoundLibrary.NOTIFICATION_APPLIED, address("prefix.applied")),

	}

}