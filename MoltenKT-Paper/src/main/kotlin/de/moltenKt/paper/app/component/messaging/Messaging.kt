package de.moltenKt.paper.app.component.messaging

import de.moltenKt.core.extension.container.firstOrNull
import de.moltenKt.core.extension.container.removeAll
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.tool.effect.sound.SoundLibrary
import de.moltenKt.unfold.extension.asStyledComponent
import org.bukkit.entity.Player

internal object Messaging {

    fun sendMessage(sender: Player, receiver: Player, message: String) {

        MoltenCache.messageConversationPartners.removeAll { key, value ->
            key == sender || value == sender
        }

        MoltenCache.messageConversationPartners[sender] = receiver

        val you = lang["system.message.style.you"]
        val format = lang["system.message.style"]

        sender.sendMessage(format.replaceVariables(
            "sender" to "<gray>$you",
            "receiver" to "<gold>${receiver.name}",
        ).asStyledComponent
            .replaceText {
                it.replacement(message).match("\\[message]")
            }
        )

        receiver.sendMessage(format.replaceVariables(
            "sender" to "<gray>${sender.name}",
            "receiver" to "<gold>$you",
        ).asStyledComponent
            .replaceText {
                it.replacement(message).match("\\[message]")
            }
        )

        receiver.playSoundEffect(SoundLibrary.NOTIFICATION_GENERAL)

    }

    /**
     * Returns, if the sender was in a conversion, to reply to.
     */
    fun sendReply(sender: Player, message: String): Boolean {
        val reply = MoltenCache.messageConversationPartners.firstOrNull {
            it.key == sender || it.value == sender
        }?.let {
            if (it.key == sender) it.value else it.key
        }

        return if (reply != null) {
            sendMessage(sender, reply, message)
            true
        } else
            false

    }

}