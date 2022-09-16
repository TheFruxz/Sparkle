package de.moltenKt.paper.app.component.messaging

import de.moltenKt.core.extension.container.firstOrNull
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.tool.effect.sound.SoundLibrary
import de.moltenKt.unfold.extension.colorOf
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

internal object Messaging {

    private fun Player.sendPrivateMessage(from: Player, message: String) = listOf(this, from).forEach { player -> player.sendMessage(
        text {
            this + (text(from.displayName().colorIfAbsent(NamedTextColor.GRAY)).takeIf { from != player } ?: text("YOU").color(NamedTextColor.GRAY))
            this + text(" → ").color(colorOf(166, 170, 191))
            this + (text(displayName().colorIfAbsent(NamedTextColor.GRAY)).takeIf { this@sendPrivateMessage != player } ?: text("YOU").color(NamedTextColor.GRAY))
            this + text(" ⏵ ").dyeGray()
            this + text(message).color(colorOf(204, 204, 204))
        }
    ) }

    fun sendMessage(sender: Player, receiver: Player, message: String) {

        MoltenCache.messageConversationPartners = MoltenCache.messageConversationPartners.filter {
            it.key != receiver || it.value != receiver
        }

        receiver.sendPrivateMessage(sender, message)

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