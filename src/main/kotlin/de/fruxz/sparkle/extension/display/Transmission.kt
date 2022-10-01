package de.fruxz.sparkle.extension.display

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.tool.display.message.Transmission
import de.fruxz.sparkle.tool.display.message.Transmission.Level
import de.fruxz.stacked.extension.asStyledComponent
import de.fruxz.stacked.extension.lines
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentIteratorType
import net.kyori.adventure.text.ComponentLike

fun Iterable<ComponentLike>.message(vararg participant: InterchangeExecutor) =
	Transmission(content = this.toList().map(ComponentLike::asComponent))
		.participants(participant.toList())

fun Iterable<ComponentLike>.notification(level: Level, vararg participant: InterchangeExecutor) =
	Transmission(content = this.toList().map(ComponentLike::asComponent), level = level)
		.promptSound(level.promptSound)
		.participants(participant.toList())

fun ComponentLike.message(vararg participant: InterchangeExecutor) = lines().message(*participant)

fun ComponentLike.notification(level: Level, vararg participant: InterchangeExecutor) = lines().notification(level, *participant)

fun String.message(vararg participants: InterchangeExecutor) = lines().map(String::asStyledComponent).message(*participants)

fun String.notification(level: Level, vararg participants: InterchangeExecutor) = lines().map(String::asStyledComponent).notification(level, *participants)
