package de.jet.app.interchange

import de.jet.library.extension.collection.mapToString
import de.jet.library.extension.display.notification
import de.jet.library.structure.app.App
import de.jet.library.structure.command.CompletionVariable
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.command.next
import de.jet.library.structure.command.plus
import de.jet.library.tool.display.message.Transmission.Level

class JETInterchange(vendor: App) : Interchange(
	vendor = vendor,
	label = "jet",
	requiresAuthorization = false,
	completion = buildCompletion {
		next("This") + "is" + "unbelievable" + "good!"
		next(CompletionVariable(vendor, "int", false) { (0..1000).mapToString() })
	}
) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {

		val sounds = Level.values()[parameters[1].toInt()]

		"This is ${sounds.name}!"
			.notification(sounds, executor)
			.display()

		SUCCESS

	}
}