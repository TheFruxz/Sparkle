package de.jet.library.extension.display

import de.jet.library.tool.display.message.Transmission
import de.jet.library.tool.display.message.Transmission.Level
import de.jet.library.tool.effect.sound.SoundMelody
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

fun String.message(vararg participants: CommandSender) =
	Transmission(content = Component.text().append(Component.text(this)))
		.participants(participants.toList())

fun String.notification(level: Level, vararg participants: CommandSender) =
	Transmission(content = Component.text().append(Component.text(this)), level = level)
		.promptSound(level.promptSound)
		.participants(participants.toList())