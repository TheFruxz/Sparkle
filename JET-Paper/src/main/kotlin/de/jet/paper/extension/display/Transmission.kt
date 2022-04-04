package de.jet.paper.extension.display

import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.display.message.Transmission.Level
import de.jet.unfold.extension.asStyledComponent
import net.kyori.adventure.text.ComponentLike

fun ComponentLike.message(vararg participant: InterchangeExecutor) =
	Transmission(content = this.asComponent())
		.participants(participant.toList())

fun ComponentLike.notification(level: Level, vararg participant: InterchangeExecutor) =
	Transmission(content = this.asComponent(), level = level)
		.promptSound(level.promptSound)
		.participants(participant.toList())

fun String.message(vararg participants: InterchangeExecutor) = asStyledComponent.message(*participants)

fun String.notification(level: Level, vararg participants: InterchangeExecutor) = asStyledComponent.notification(level, *participants)
