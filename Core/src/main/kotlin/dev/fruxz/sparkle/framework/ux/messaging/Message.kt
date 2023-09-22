package dev.fruxz.sparkle.framework.ux.messaging

import dev.fruxz.stacked.extension.asStyledComponents
import dev.fruxz.stacked.extension.lines
import net.kyori.adventure.text.ComponentLike
import org.bukkit.command.CommandSender

// Messages

fun List<ComponentLike>.transmission(vararg participants: CommandSender) =
    Transmission(participants = participants.toSet(), content = flatMap { it.lines })

fun ComponentLike.transmission(vararg participants: CommandSender) =
    this.lines.transmission(*participants)

infix fun ComponentLike.transmission(participant: CommandSender) =
    this.lines.transmission(participant)

fun String.transmission(vararg participants: CommandSender) =
    asStyledComponents.transmission(*participants)

infix fun String.transmission(participant: CommandSender) =
    asStyledComponents.transmission(participant)

// Notification

fun List<ComponentLike>.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    Transmission(participants = participants.toSet(), content = flatMap { it.lines }, theme = theme)

fun ComponentLike.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    this.lines.transmission(*participants, theme = theme)
fun String.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    asStyledComponents.transmission(*participants, theme = theme)