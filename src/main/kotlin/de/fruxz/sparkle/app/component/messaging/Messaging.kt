package de.fruxz.sparkle.app.component.messaging

import de.fruxz.ascend.extension.container.firstOrNull
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.effect.playSoundEffect
import de.fruxz.sparkle.tool.effect.sound.SoundLibrary
import de.fruxz.stacked.extension.colorOf
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
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

        SparkleCache.messageConversationPartners = SparkleCache.messageConversationPartners.filter {
            it.key != receiver || it.value != receiver
        }

        receiver.sendPrivateMessage(sender, message)

        receiver.playSoundEffect(SoundLibrary.NOTIFICATION_GENERAL)

    }

    /**
     * Returns, if the sender was in a conversion, to reply to.
     */
    fun sendReply(sender: Player, message: String): Boolean {
        val reply = SparkleCache.messageConversationPartners.firstOrNull {
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