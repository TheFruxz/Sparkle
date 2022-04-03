package de.jet.paper.app.component.chat

import de.jet.paper.extension.display.buildTextComponent
import de.jet.paper.extension.paper.consoleSender
import de.jet.paper.extension.paper.getPlayer
import de.jet.paper.extension.paper.onlinePlayers
import de.jet.paper.structure.app.event.EventListener
import de.jet.unfold.extension.legacyString
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import java.util.*

internal class ChatListener : EventListener() {

	@EventHandler
	fun onChat(event: AsyncChatEvent) {
		val player = event.player
		val notify = mutableSetOf<UUID>()
		val message = buildTextComponent {
			event.message().legacyString.split(" ").forEach { snipped ->
				val tagged = getPlayer(snipped.removePrefix("@"))

				append(Component.text(" "))

				if (snipped.startsWith("@") && snipped.length > 2 && tagged != null && !notify.contains(tagged.uniqueId)) {
					append(Component.text("@${tagged.displayName().legacyString}", NamedTextColor.YELLOW))
					notify.add(tagged.uniqueId)
				} else if (snipped.startsWith("#") && snipped.length > 1) {
					append(Component.text(snipped, NamedTextColor.YELLOW))
				} else if (snipped.equals("[item]", true)) {
					append(player.inventory.itemInMainHand.displayName().hoverEvent(player.inventory.itemInMainHand).color(
						NamedTextColor.GRAY))
				} else
					append(Component.text(snipped, NamedTextColor.GRAY))

			}
		}
		val sender: TextComponent = buildTextComponent {
			append(player.displayName().color(NamedTextColor.GRAY))
		}

		buildTextComponent {

			append(Component.text("▶ ", NamedTextColor.DARK_GRAY))
			append(sender)
			append(Component.text(" |", NamedTextColor.DARK_GRAY))
			append(message)

		}.let { result ->
			(onlinePlayers + consoleSender).forEach { receiver ->

				receiver.sendMessage(result)

				if (receiver is Player && notify.contains(receiver.uniqueId))
					receiver.playSound(Sound.sound(BLOCK_NOTE_BLOCK_PLING, MASTER, 1F, 2F))

			}
		}

		event.isCancelled = true

	}

}