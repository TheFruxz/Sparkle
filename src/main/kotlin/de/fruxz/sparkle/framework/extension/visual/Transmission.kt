package de.fruxz.sparkle.framework.extension.visual

import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.visual.message.Transmission
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import dev.fruxz.stacked.extension.asStyledComponent
import dev.fruxz.stacked.extension.lines
import net.kyori.adventure.text.ComponentLike

fun Iterable<ComponentLike>.message(vararg participant: InterchangeExecutor) =
	Transmission(content = this.map(ComponentLike::asComponent))
		.participants(participant.toSet())

fun Iterable<ComponentLike>.notification(type: TransmissionAppearance, vararg participant: InterchangeExecutor) =
	Transmission(content = this.map(ComponentLike::asComponent), experience = type)
		.promptSound(type.promptSound)
		.participants(participant.toSet())

fun ComponentLike.message(vararg participant: InterchangeExecutor) = lines.message(*participant)

fun ComponentLike.notification(type: TransmissionAppearance, vararg participant: InterchangeExecutor) = lines.notification(type, *participant)

fun String.message(vararg participants: InterchangeExecutor) = lines().map(String::asStyledComponent).message(*participants)

fun String.notification(type: TransmissionAppearance, vararg participants: InterchangeExecutor) = lines().map(String::asStyledComponent).notification(type, *participants)
