package de.fruxz.sparkle.framework.visual.message

import dev.fruxz.ascend.extension.dump
import dev.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.sparkle.framework.effect.EntityBasedEffect
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.extension.consoleSender
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.onlinePlayers
import de.fruxz.sparkle.framework.visual.message.DisplayType.*
import dev.fruxz.stacked.extension.asComponent
import dev.fruxz.stacked.extension.asStyledComponent
import dev.fruxz.stacked.extension.asStyledString
import dev.fruxz.stacked.extension.joinToComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.title.Title
import org.bukkit.entity.Entity

data class Transmission(
	var prefix: Component? = null,
	var content: List<Component> = listOf(Component.empty()),
	var participants: Set<InterchangeExecutor> = setOf(),
	var withoutPrefix: Boolean = false,
	var displayType: DisplayType = DISPLAY_CHAT,
	var promptSoundEffect: SoundEffect? = null,
	var experience: TransmissionAppearance = TransmissionAppearance.GENERAL,
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

	infix fun participants(participants: Collection<InterchangeExecutor>) = edit { this.participants = participants.toSet() }

	infix fun participants(participants: Array<InterchangeExecutor>) = participants(participants.toList())

	infix fun withoutPrefix(withoutPrefix: Boolean) = edit { this.withoutPrefix = withoutPrefix }

	infix fun displayType(displayType: DisplayType) = edit { this.displayType = displayType }

	infix fun promptSound(soundEffect: SoundEffect?) = edit { this.promptSoundEffect = soundEffect }

	fun display(receivers: Set<InterchangeExecutor> = participants): Transmission {
		var nextRound = setOf<Entity>()

		val prefix = this.prefix ?: experience.prefix.invoke().asComponent()

		val displayObject = content.map { prefix.append(it) }

		for (receiver in receivers) {

			when (displayType) {
				DISPLAY_CHAT -> displayObject.forEach { receiver.sendMessage(it) }
				DISPLAY_ACTIONBAR -> receiver.sendActionBar(displayObject.joinToComponent())
				DISPLAY_TITLE -> receiver.showTitle(Title.title(displayObject.joinToComponent(), Component.empty()))
				DISPLAY_SUBTITLE -> receiver.showTitle(Title.title(Component.empty(), displayObject.joinToComponent()))
			}

			if (receiver is Entity) nextRound += receiver
		}

		promptSoundEffect?.play(*nextRound.toTypedArray())

		return this
	}

	fun broadcast() = this
		.copy()
		.participants(onlinePlayers + consoleSender)
		.display()

	override fun play(vararg entities: Entity?) =
		copy().participants(entities.mapNotNull { it?.takeIfInstance<InterchangeExecutor>() }).display().dump()

	override fun toString() = content.joinToString("\n", transform = Component::asStyledString)

}