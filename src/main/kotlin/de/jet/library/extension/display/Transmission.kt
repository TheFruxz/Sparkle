package de.jet.library.extension.display

import de.jet.library.tool.display.message.Transmission
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

fun String.message(vararg participants: CommandSender) = Transmission(content = Component.text().append(Component.text(this)))