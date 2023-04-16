package dev.fruxz.sparkle.framework.ux.messaging

import dev.fruxz.stacked.extension.asStyledComponents
import net.kyori.adventure.text.ComponentLike
import org.bukkit.command.CommandSender

// Messages

fun List<ComponentLike>.transmission(vararg participants: CommandSender) =
    Transmission(participants = participants.toSet(), content = map { it.asComponent() })

fun ComponentLike.transmission(vararg participants: CommandSender) =
    listOf(this).transmission(*participants)

fun String.transmission(vararg participants: CommandSender) =
    asStyledComponents.transmission(*participants)

// Notification

fun List<ComponentLike>.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    Transmission(participants = participants.toSet(), content = map { it.asComponent() }, theme = theme)

fun ComponentLike.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    listOf(this).transmission(*participants, theme = theme)
fun String.transmission(vararg participants: CommandSender, theme: Transmission.Theme?) =
    asStyledComponents.transmission(*participants, theme = theme)