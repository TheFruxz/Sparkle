package dev.fruxz.sparkle.framework.ux.messaging

import dev.fruxz.sparkle.framework.system.consoleSender
import dev.fruxz.sparkle.framework.system.onlinePlayers
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundEffect
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundLibrary
import dev.fruxz.stacked.extension.asStyledComponent
import dev.fruxz.stacked.extension.asStyledString
import dev.fruxz.stacked.extension.joinToComponent
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity

data class Transmission(
    var prefix: Component? = null,
    var content: List<Component> = listOf(Component.empty()),
    var participants: Set<CommandSender> = emptySet(),
    var hidePrefix: Boolean = false,
    var displayType: DisplayType = DisplayType.CHAT,
    var soundEffect: SoundEffect? = null,
    var theme: Theme? = null,
) {

    constructor(message: String) : this(content = listOf(message.asStyledComponent))
    constructor(component: ComponentLike) : this(content = listOf(component.asComponent()))
    constructor(vararg messages: String) : this(content = messages.map { it.asStyledComponent })
    constructor(vararg components: ComponentLike) : this(content = components.map { it.asComponent() })

    private val defaultPrefix = (text("\uD83D\uDD25 ").style(TextColor.color(255, 0, 64)) + text("» ").style(TextColor.color(74, 74, 74)))

    fun display(receivers: Set<CommandSender> = participants): Transmission = apply {
        val computedPrefix = this.prefix ?: theme?.prefix ?: defaultPrefix
        val computedSound = this.soundEffect ?: theme?.sound
        val displayObject = content.map { computedPrefix.append(it.colorIfAbsent(TextColor.color(158, 158, 158))) }

        receivers.forEach { receiver ->

            when (displayType) {
                DisplayType.CHAT -> displayObject.forEach(receiver::sendMessage)
                DisplayType.ACTION_BAR -> receiver.sendActionBar(displayObject.joinToComponent())
            }

            if (receiver is Entity) {
                computedSound?.play(receiver, sticky = true)
            }

        }

    }

    fun broadcast() = this
        .copy(participants = onlinePlayers + consoleSender)
        .display()

    override fun toString() = content.joinToString("\n", transform = Component::asStyledString)

    interface Theme {

        val prefix: Component
        val sound: SoundEffect?

        enum class Default : Theme {

            GENERAL,
            SUCCESS,
            APPLIED,
            INFO,
            WARNING,
            ERROR,
            PROCESS,
            LEVEL,
            PAYMENT,
            ATTENTION;

            override val prefix: Component =
                (text("\uD83D\uDD25 ").style(TextColor.color(255, 0, 64)) + text("» ").style(TextColor.color(74, 74, 74)))

            override val sound: SoundEffect? by lazy {
                when (this) {
                    GENERAL -> SoundLibrary.NOTIFICATION_GENERAL
                    SUCCESS -> SoundLibrary.NOTIFICATION_GENERAL
                    APPLIED -> SoundLibrary.NOTIFICATION_APPLIED
                    INFO -> SoundLibrary.NOTIFICATION_GENERAL
                    WARNING -> SoundLibrary.NOTIFICATION_WARNING
                    ERROR -> SoundLibrary.NOTIFICATION_ERROR
                    PROCESS -> SoundLibrary.NOTIFICATION_PROCESS
                    LEVEL -> SoundLibrary.NOTIFICATION_LEVEL
                    PAYMENT -> SoundLibrary.NOTIFICATION_PAYMENT
                    ATTENTION -> SoundLibrary.NOTIFICATION_ATTENTION
                }.sound
            }

        }

        enum class Color : Theme {

            GENERAL,
            SUCCESS,
            APPLIED,
            INFO,
            WARNING,
            ERROR,
            PROCESS,
            LEVEL,
            PAYMENT,
            ATTENTION;

            override val prefix: Component by lazy {
                text("\uD83D\uDD25 ").style(when (this) {
                    GENERAL -> TextColor.color(255, 0, 64)
                    SUCCESS -> TextColor.color(0, 247, 107)
                    APPLIED -> TextColor.color(110, 255, 48)
                    INFO -> TextColor.color(0, 191, 255)
                    WARNING -> TextColor.color(255, 153, 0)
                    ERROR -> TextColor.color(222, 4, 41)
                    PROCESS -> TextColor.color(78, 129, 217)
                    LEVEL -> TextColor.color(157, 0, 255)
                    PAYMENT -> TextColor.color(255, 200, 0)
                    ATTENTION -> TextColor.color(255, 0, 0)
                }) + text("» ").style(TextColor.color(74, 74, 74))
            }

            override val sound: SoundEffect? by lazy {
                when (this) {
                    GENERAL -> SoundLibrary.NOTIFICATION_GENERAL
                    SUCCESS -> SoundLibrary.NOTIFICATION_GENERAL
                    APPLIED -> SoundLibrary.NOTIFICATION_APPLIED
                    INFO -> SoundLibrary.NOTIFICATION_GENERAL
                    WARNING -> SoundLibrary.NOTIFICATION_WARNING
                    ERROR -> SoundLibrary.NOTIFICATION_ERROR
                    PROCESS -> SoundLibrary.NOTIFICATION_PROCESS
                    LEVEL -> SoundLibrary.NOTIFICATION_LEVEL
                    PAYMENT -> SoundLibrary.NOTIFICATION_PAYMENT
                    ATTENTION -> SoundLibrary.NOTIFICATION_ATTENTION
                }.sound
            }

        }

    }

    enum class DisplayType {
        CHAT,
        ACTION_BAR,
    }

}
