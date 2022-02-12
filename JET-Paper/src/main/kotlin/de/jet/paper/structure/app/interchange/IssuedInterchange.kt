package de.jet.paper.structure.app.interchange

import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.completion.emptyInterchangeStructure
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.display.message.Transmission.Level.ERROR

class IssuedInterchange(
	label: String,
	aliases: Set<String>,
) : Interchange(
	label = label,
	aliases = aliases,
	protectedAccess = false,
	completion = emptyInterchangeStructure(),
) {

	override val execution: InterchangeAccess.() -> InterchangeResult = {

		lang("interchange.structure.issue.register")
			.notification(ERROR, executor)
			.display()

		SUCCESS
	}

}