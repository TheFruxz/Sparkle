package de.jet.minecraft.app.component.chat

import de.jet.library.extension.paper.getPlayer
import de.jet.library.extension.paper.onlinePlayers
import de.jet.minecraft.extension.display.buildTextComponent
import de.jet.minecraft.extension.paper.legacyString
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.minecraft.structure.component.SmartComponent
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING
import org.bukkit.event.EventHandler
import java.util.*
import net.kyori.adventure.text.Component as AdventureComponent

internal class JetChatComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "Chat"

	override fun component() {
		listener(Listener(vendor))
	}

	class Listener(override val vendor: App) : EventListener {

		@EventHandler
		fun onChat(event: AsyncChatEvent) {
			val player = event.player

			val notify = mutableSetOf<UUID>()

			val message: TextComponent = buildTextComponent {
				event.message().legacyString.split(" ").forEach { snipped ->
					val tagged = getPlayer(snipped.removePrefix("@"))

					append(AdventureComponent.text(" "))

					if (snipped.startsWith("@") && snipped.length > 2 && tagged != null && !notify.contains(tagged.uniqueId)) {
						append(AdventureComponent.text("@${tagged.displayName().legacyString}", NamedTextColor.YELLOW))
						notify.add(tagged.uniqueId)
					} else if (snipped.startsWith("#") && snipped.length > 1) {
						append(AdventureComponent.text(snipped, NamedTextColor.YELLOW))
					} else if (snipped.equals("[item]", true)) {
						append(player.inventory.itemInMainHand.displayName().hoverEvent(player.inventory.itemInMainHand).color(NamedTextColor.GRAY))
					} else
						append(AdventureComponent.text(snipped, NamedTextColor.GRAY))

				}
			}

			val sender: TextComponent = buildTextComponent {
				append(player.displayName().color(NamedTextColor.GRAY))
			}

			buildTextComponent {

				append(AdventureComponent.text("▶ ", NamedTextColor.DARK_GRAY))
				append(sender)
				append(AdventureComponent.text(" |", NamedTextColor.DARK_GRAY))
				append(message)

			}.let { result ->
				onlinePlayers.forEach { receiver ->

					receiver.sendMessage(result)

					if (notify.contains(receiver.uniqueId))
						receiver.playSound(Sound.sound(BLOCK_NOTE_BLOCK_PLING, MASTER, 1F, 2F))

				}
			}

			event.isCancelled = true

		}

	}

}