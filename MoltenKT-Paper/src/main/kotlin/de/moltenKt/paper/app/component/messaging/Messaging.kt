package de.moltenKt.paper.app.component.messaging

import de.moltenKt.core.extension.container.firstOrNull
import de.moltenKt.core.extension.container.removeAll
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.message
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.unfold.text
import org.bukkit.entity.Player

object Messaging {

    fun sendMessage(sender: Player, receiver: Player, message: String) {

        MoltenCache.messageConversationPartners.removeAll { key, value ->
            key == sender || value == sender
        }

        MoltenCache.messageConversationPartners[sender] = receiver

        message.message(sender)
            .prefix(text("<gray>YOU <dark_gray>-> <gold>${receiver.name} <gray>>> "))
            .display()

        message.notification(Transmission.Level.INFO, receiver)
            .prefix(text("<gray>${sender.name} <dark_gray>-> <gold>YOU <gray>>> "))
            .display()

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