package de.moltenKt.paper.tool.display.message

import de.fruxz.ascend.extension.dump
import de.fruxz.ascend.tool.smart.positioning.Address
import de.fruxz.ascend.tool.smart.positioning.Address.Companion.address
import de.moltenKt.paper.app.MoltenData
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.paper.consoleSender
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.tool.display.message.DisplayType.*
import de.moltenKt.paper.tool.effect.EntityBasedEffect
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import de.moltenKt.paper.tool.effect.sound.SoundLibrary
import de.fruxz.stacked.extension.asComponent
import de.fruxz.stacked.extension.asStyledComponent
import de.fruxz.stacked.extension.asStyledString
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.title.Title
import org.bukkit.entity.Entity

data class Transmission(
	var prefix: Component? = null,
	var content: List<Component> = listOf(Component.empty()),
	var participants: MutableList<InterchangeExecutor> = mutableListOf(),
	var withoutPrefix: Boolean = false,
	var displayType: DisplayType = DISPLAY_CHAT,
	var promptSoundEffect: SoundEffect? = null,
	var level: Level = Level.GENERAL,
	var prefixByLevel: Boolean = true,
) : EntityBasedEffect {

	constructor(legacyMessage: String) : this(content = listOf(legacyMessage.asStyledComponent))

	constructor(componentLike: ComponentLike) : this(content = listOf(componentLike.asComponent()))

	constructor(legacyMessageLines: Array<String>) : this(content = legacyMessageLines.map { it.asComponent })

	constructor(componentLikeLines: List<ComponentLike>) : this(content = componentLikeLines.map { it.asComponent() })

	infix fun edit(action: Transmission.() -> Unit) = apply(action)

	infix fun prefix(prefix: Component) = edit {
		prefixByLevel = false
		this.prefix = prefix
	}

	infix fun content(content: ComponentLike) = edit { this.content = listOf(content.asComponent()) }

	infix fun content(content: String) = edit { this.content = listOf(content.asComponent) }

	infix fun content(content: List<Component>) = edit { this.content = content }

	@JvmName("contentStrings")
	infix fun content(content: List<String>) = edit { this.content = content.map { it.asComponent } }

	infix fun participants(participants: Collection<InterchangeExecutor>) = edit { this.participants = participants.toMutableList() }

	infix fun participants(participants: Array<InterchangeExecutor>) = participants(participants.toList())

	infix fun withoutPrefix(withoutPrefix: Boolean) = edit { this.withoutPrefix = withoutPrefix }

	infix fun displayType(displayType: DisplayType) = edit { this.displayType = displayType }

	infix fun promptSound(soundEffect: SoundEffect?) = edit { this.promptSoundEffect = soundEffect }

	fun display(): Transmission {
		val nextRound = mutableSetOf<Entity>()

		val prefix = (MoltenData.systemConfig.prefix[level.prefixLink.addressString.takeIf { prefixByLevel } ?: "general"] ?: "<dark_gray>⏵ ").asStyledComponent

		val displayObject = content.map { prefix.append(it) }

		for (participant in participants) {

			when (displayType) {
				DISPLAY_CHAT -> displayObject.forEach { participant.sendMessage(it) }
				DISPLAY_ACTIONBAR -> participant.sendActionBar(displayObject.first())
				DISPLAY_TITLE -> participant.showTitle(Title.title(displayObject.first(), Component.empty()))
				DISPLAY_SUBTITLE -> participant.showTitle(Title.title(Component.empty(), displayObject.first()))
			}

			if (participant is Entity)
				nextRound.add(participant)
		}

		promptSoundEffect?.play(*nextRound.toTypedArray())

		return this
	}

	fun broadcast() = this
		.copy()
		.participants(onlinePlayers + consoleSender)
		.display()

	override fun play(vararg entities: Entity?) =
		copy().participants(entities.mapNotNull {
			if (it is InterchangeExecutor) it else null
		}).display().dump()

	override fun toString() = content.map(Component::asStyledString).joinToString("\n")

	enum class Level(
		val promptSound: SoundEffect,
		val prefixLink: Address<Level>,
	) {

		GENERAL(SoundLibrary.NOTIFICATION_GENERAL, address("prefix.general")),
		PROCESS(SoundLibrary.NOTIFICATION_PROCESS, address("prefix.process")),
		FAIL(SoundLibrary.NOTIFICATION_FAIL, address("prefix.fail")),
		ERROR(SoundLibrary.NOTIFICATION_ERROR, address("prefix.error")),
		LEVEL(SoundLibrary.NOTIFICATION_LEVEL, address("prefix.level")),
		WARNING(SoundLibrary.NOTIFICATION_WARNING, address("prefix.warning")),
		ATTENTION(SoundLibrary.NOTIFICATION_ATTENTION, address("prefix.attention")),
		PAYMENT(SoundLibrary.NOTIFICATION_PAYMENT, address("prefix.payment")),
		APPLIED(SoundLibrary.NOTIFICATION_APPLIED, address("prefix.applied")),

	}

}