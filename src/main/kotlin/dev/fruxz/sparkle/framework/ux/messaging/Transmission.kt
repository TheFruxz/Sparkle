package dev.fruxz.sparkle.framework.ux.messaging

import dev.fruxz.sparkle.framework.system.consoleSender
import dev.fruxz.sparkle.framework.system.onlinePlayers
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundEffect
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
    var soundEffect: Any? = null, // TODO Sound Effect
    var theme: Theme = Theme.Template.GENERAL,
) {

    constructor(message: String) : this(content = listOf(message.asStyledComponent))
    constructor(component: ComponentLike) : this(content = listOf(component.asComponent()))
    constructor(vararg messages: String) : this(content = messages.map { it.asStyledComponent })
    constructor(vararg components: ComponentLike) : this(content = components.map { it.asComponent() })

    fun display(receivers: Set<CommandSender> = participants): Transmission = apply {
        val computedPrefix = this.prefix ?: theme.prefix
        val displayObject = content.map { computedPrefix.append(it) }

        receivers.forEach { receiver ->

            when (displayType) {
                DisplayType.CHAT -> displayObject.forEach(receiver::sendMessage)
                DisplayType.ACTION_BAR -> receiver.sendActionBar(displayObject.joinToComponent())
            }

            if (receiver is Entity) {
                // TODO play sound effect
            }

        }

    }

    fun broadcast() = this
        .copy(participants = onlinePlayers + consoleSender) // TODO bukkit extension values for both
        .display()

    override fun toString() = content.joinToString("\n", transform = Component::asStyledString)

    interface Theme {

        val prefix: Component
        val sound: SoundEffect?

        enum class Template : Theme {

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
                    GENERAL -> TextColor.color(204, 204, 204)
                    SUCCESS -> TextColor.color(0, 232, 70)
                    INFO -> TextColor.color(0, 154, 219)
                    WARNING -> TextColor.color(255, 153, 0) // 2 times minecraft:block.note_block.pling 2 .6
                    ERROR -> TextColor.color(222, 4, 41)
                    else -> TextColor.color(204, 204, 204) // TODO not real
                }) + text("» ").style(TextColor.color(74, 74, 74))
            }

            override val sound: SoundEffect?
                get() = TODO("Not yet implemented")

        }

    }

    enum class DisplayType {
        CHAT,
        ACTION_BAR,
    }

}
