package de.jet.paper.structure.app.interchange

import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.emptyCompletion
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.display.message.Transmission.Level.ERROR

class IssuedInterchange(
	vendor: App,
	label: String,
	aliases: Set<String>,
) : Interchange(
	vendor = vendor,
	label = label,
	aliases = aliases,
	protectedAccess = false,
	completion = emptyCompletion(),
) {

	override val execution: InterchangeAccess.() -> InterchangeResult = {

		lang("interchange.structure.issue.register")
			.notification(ERROR, executor)
			.display()

		SUCCESS
	}

}