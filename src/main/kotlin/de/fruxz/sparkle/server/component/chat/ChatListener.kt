package de.fruxz.sparkle.server.component.chat

import de.fruxz.ascend.extension.container.replacePrefix
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.infrastructure.app.event.EventListener
import de.fruxz.sparkle.framework.util.extension.effect.playEffect
import de.fruxz.sparkle.framework.util.extension.effect.soundOf
import de.fruxz.sparkle.framework.util.extension.consoleSender
import de.fruxz.sparkle.framework.util.extension.onlinePlayers
import de.fruxz.sparkle.framework.util.extension.playerOrNull
import de.fruxz.stacked.buildComponent
import de.fruxz.stacked.extension.asComponent
import de.fruxz.stacked.extension.asPlainString
import de.fruxz.stacked.extension.asString
import de.fruxz.stacked.extension.asStyledComponent
import de.fruxz.stacked.extension.asStyledString
import de.fruxz.stacked.extension.replace
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import io.papermc.paper.event.player.AsyncChatEvent
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

internal class ChatListener : EventListener() {

	private val mentionPingSound = soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 2)

	@EventHandler
	fun onChat(event: AsyncChatEvent) = with(event) {
		val setup = ChatComponent.setup
		var notifiedPlayers = setOf<Player>()

		var message: Component = buildComponent {

			message().asPlainString.replace("<", "''<'").let {
				tryOrNull { it.asStyledComponent } ?: it.asComponent
			}.asPlainString.replacePrefix("./", "/").split(" ").forEach { snippet ->
				// Per snipped computation
				val tagged = playerOrNull(snippet.removePrefix("@"))?.also {
					notifiedPlayers += it
				}

				when {
					setup.mentions.enabled && snippet.startsWith("@") && snippet.length > 2 && tagged != null -> {
						this + "<${setup.mentions.mentionColor}>@${tagged.displayName().asString}</${setup.mentions.mentionColor}>"
							.asStyledComponent
							.clickEvent(ClickEvent.suggestCommand("/msg ${tagged.name}"))
							.hoverEvent(tagged)
					}
					setup.hashTags.enabled && snippet.startsWith("#") && snippet.length > 2 -> {
						this + "<${setup.hashTags.hashTagColor}>$snippet</${setup.hashTags.hashTagColor}>"
							.asStyledComponent
							.clickEvent(null)
							.hoverEvent(null)
					}
					setup.commands.enabled && snippet.startsWith("/") && snippet.length > 2 -> {
						this + "<${setup.commands.commandColor}>$snippet</${setup.commands.commandColor}>"
							.asStyledComponent
							.clickEvent(ClickEvent.suggestCommand(snippet))
							.hoverEvent("<${setup.commands.commandColor}>$snippet</${setup.commands.commandColor}>".asStyledComponent)
					}
					setup.items.enabled && snippet.equals("[items]", true) -> {
						this + "<${setup.items.itemColor}>${player.inventory.itemInMainHand.displayName().asStyledString}</${setup.items.itemColor}>"
							.asStyledComponent
							.clickEvent(null)
							.hoverEvent(player.inventory.itemInMainHand)
					}
					else -> this + "<${setup.messageColor}>$snippet</${setup.messageColor}>".asStyledComponent.hoverEvent(null).clickEvent(null)
				}

				this + Component.space()

			}

		}

		if (setup.allowExtensions) {
			ChatComponent.chatExtensions.forEach {
				message = message.replaceText(it)
			}
		}

		buildComponent {

			this + (when {
				ChatComponent.usePlaceholderAPI -> PlaceholderAPI.setPlaceholders(player, setup.chatFormat)
				else -> setup.chatFormat
			}).asStyledComponent
				.replaceText {
					it.match("\\[displayName]")
					it.replacement(buildComponent {
						this + text(player.displayName()) {
							hoverEvent(player)
							clickEvent(ClickEvent.suggestCommand("/msg ${player.name}"))
						}
					})
				}
				.replaceText {
					it.match("\\[name]")
					it.replacement(player.name)
				}
				.replaceText {
					it.match("\\[playerListName]")
					it.replacement(buildComponent {
						this + text(player.playerListName()) {
							hoverEvent(player)
							clickEvent(ClickEvent.suggestCommand("/msg ${player.name}"))
						}
					})
				}
				.replaceText {
					it.match("\\[customName]")
					it.replacement(buildComponent {
						this + text(player.customName() ?: Component.empty()) {
							hoverEvent(player)
							clickEvent(ClickEvent.suggestCommand("/msg ${player.name}"))
						}
					})
				}
				.replaceText {
					it.match("\\[message]")
					it.replacement(message)
				}
				.replace("''<'", "<")

		}.let { result ->
			(onlinePlayers + consoleSender).forEach { receiver ->

				receiver.sendMessage(result)

				if (receiver is Player && receiver in notifiedPlayers)
					receiver.playEffect(mentionPingSound)

			}
		}

		isCancelled = true

	}

}