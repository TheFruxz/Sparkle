package de.jet.paper.extension.display

import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.paper.adventureComponent
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.display.message.Transmission.Level

fun String.message(vararg participants: InterchangeExecutor) =
	Transmission(content = this.adventureComponent.toBuilder())
		.participants(participants.toList())

fun String.notification(level: Level, vararg participants: InterchangeExecutor) =
	Transmission(content = this.adventureComponent.toBuilder(), level = level)
		.promptSound(level.promptSound)
		.participants(participants.toList())