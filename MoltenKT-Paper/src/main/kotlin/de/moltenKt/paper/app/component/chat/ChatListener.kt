package de.moltenKt.paper.app.component.chat

import de.moltenKt.core.extension.container.replacePrefix
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.paper.extension.display.buildTextComponent
import de.moltenKt.paper.extension.paper.consoleSender
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.extension.paper.playerOrNull
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.unfold.extension.asComponent
import de.moltenKt.unfold.extension.asString
import de.moltenKt.unfold.extension.asStyledComponent
import de.moltenKt.unfold.extension.asStyledString
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

internal class ChatListener : EventListener() {

	@EventHandler
	fun onChat(event: AsyncChatEvent) = with(event) {
		val setup = ChatComponent.setup
		var notifiedPlayers = setOf<Player>()

		var message: Component = buildTextComponent {

			message().asString.replacePrefix("./", "/").split(" ").forEach { snipped ->
				val tagged = playerOrNull(snipped.removePrefix("@"))

				if (setup.mentions.enabled && snipped.startsWith("@") && snipped.length > 2 && tagged != null && !notifiedPlayers.contains(
						tagged
					)
				) {
					append(
						"<${setup.mentions.mentionColor}>@${tagged.displayName().asString}</${setup.mentions.mentionColor}>".asStyledComponent
							.hoverEvent(tagged).clickEvent(ClickEvent.suggestCommand("/msg ${tagged.name}"))
					)
					notifiedPlayers += tagged
				} else if (setup.hashTags.enabled && snipped.startsWith("#") && snipped.length > 1) {
					append("<${setup.hashTags.hashTagColor}>$snipped</${setup.hashTags.hashTagColor}>".asStyledComponent)
				} else if (setup.commands.enabled && snipped.startsWith("/") && snipped.length > 1) {
					append(
						"<${setup.commands.commandColor}>$snipped</${setup.commands.commandColor}>".asStyledComponent.clickEvent(
							ClickEvent.suggestCommand(snipped)
						)
					)
				} else if (setup.items.enabled && snipped.equals("[item]", true)) {
					append(
						"<${setup.items.itemColor}>${player.inventory.itemInMainHand.displayName().asStyledString}</${setup.items.itemColor}>".asStyledComponent.hoverEvent(
							player.inventory.itemInMainHand
						)
					)
				} else
					append("<${setup.messageColor}>$snipped</${setup.messageColor}>".asStyledComponent)

				append(Component.space())

			}

		}

		if (setup.allowExtensions) {
			ChatComponent.chatExtensions.forEach {
				message = message.replaceText(it)
			}
		}

		buildTextComponent {

			append(
				setup.chatFormat.replaceVariables(
					"displayName" to player.displayName().hoverEvent(player).clickEvent(ClickEvent.suggestCommand("/msg ${player.name}")).asStyledString,
					"name" to player.name,
					"playerListName" to player.playerListName().hoverEvent(player).clickEvent(ClickEvent.suggestCommand("/msg ${player.name}")).asStyledString
				).asStyledComponent.replaceText {
					it.match("\\[message]")
					it.replacement(message)
				})

		}.let { result ->
			(onlinePlayers + consoleSender).forEach { receiver ->

				receiver.sendMessage(result)

				if (receiver is Player && notifiedPlayers.contains(receiver))
					receiver.playSound(Sound.sound(BLOCK_NOTE_BLOCK_PLING, MASTER, 1F, 2F))

			}
		}

		isCancelled = true

	}

}