package de.moltenKt.paper.app.component.chat

import de.moltenKt.core.extension.container.replacePrefix
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.paper.extension.display.buildTextComponent
import de.moltenKt.paper.extension.effect.playEffect
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.extension.effect.soundOf
import de.moltenKt.paper.extension.paper.consoleSender
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.extension.paper.playerOrNull
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.unfold.buildComponent
import de.moltenKt.unfold.extension.asComponent
import de.moltenKt.unfold.extension.asString
import de.moltenKt.unfold.extension.asStyledComponent
import de.moltenKt.unfold.extension.asStyledString
import de.moltenKt.unfold.extension.miniMessageSerializer
import de.moltenKt.unfold.plus
import io.papermc.paper.event.player.AsyncChatEvent
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor
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

			message().asString.replacePrefix("./", "/").split(" ").forEach { snippet ->
				// Per snipped computation
				val tagged = playerOrNull(snippet.removePrefix("@"))?.also {
					notifiedPlayers += it
				}

				when {
					setup.mentions.enabled && snippet.startsWith("@") && snippet.length > 2 && tagged != null && tagged !in notifiedPlayers -> {
						this + "<${setup.mentions.mentionColor}>@${tagged.displayName().asString}</${setup.mentions.mentionColor}>".asStyledComponent
							.hoverEvent(tagged).clickEvent(ClickEvent.suggestCommand("/msg ${tagged.name}"))
					}
					setup.hashTags.enabled && snippet.startsWith("#") && snippet.length > 2 -> {
						this + "<${setup.hashTags.hashTagColor}>$snippet</${setup.hashTags.hashTagColor}>".asStyledComponent.clickEvent(null).hoverEvent(null)
					}
					setup.commands.enabled && snippet.startsWith("/") && snippet.length > 2 -> {
						this + "<${setup.commands.commandColor}>$snippet</${setup.commands.commandColor}>".asStyledComponent.clickEvent(
							ClickEvent.suggestCommand(snippet)
						).hoverEvent("<${setup.commands.commandColor}>$snippet</${setup.commands.commandColor}>".asStyledComponent)
					}
					setup.items.enabled && snippet.equals("[items]", true) -> {
						this + "<${setup.items.itemColor}>${player.inventory.itemInMainHand.displayName().asStyledString}</${setup.items.itemColor}>".asStyledComponent.hoverEvent(
							player.inventory.itemInMainHand
						).clickEvent(null)
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

			val displayNameReplace = "displayName" to player.displayName().hoverEvent(player).clickEvent(ClickEvent.suggestCommand("/msg ${player.name}")).asStyledString
			val nameReplace = "name" to player.name
			val playerListReplace = "playerListName" to player.playerListName().hoverEvent(player).clickEvent(ClickEvent.suggestCommand("/msg ${player.name}")).asStyledString

			this + setup.chatFormat.replaceVariables(
				displayNameReplace,
				nameReplace,
				playerListReplace,
			).run {
				if (ChatComponent.usePlaceholderAPI) {
					PlaceholderAPI.setPlaceholders(player, this) // Calls the placeholder-api for global replacing
				} else {
					this
				}
			}.asStyledComponent.replaceText {
				it.match("\\[message]")
				it.replacement(message)
			}

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