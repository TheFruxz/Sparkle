package de.jet.library.structure.app.interchange

import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.structure.app.App
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.emptyCompletion
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.tool.display.message.Transmission.Level.ERROR

class IssuedInterchange(
	vendor: App,
	label: String,
	aliases: Set<String>,
) : Interchange(
	vendor = vendor,
	label = label,
	aliases = aliases,
	requiresAuthorization = false,
	completion = emptyCompletion(),
) {

	override val execution: InterchangeAccess.() -> InterchangeResult = {

		lang("interchange.structure.issue.register")
			.notification(ERROR, executor)
			.display()

		SUCCESS
	}

}