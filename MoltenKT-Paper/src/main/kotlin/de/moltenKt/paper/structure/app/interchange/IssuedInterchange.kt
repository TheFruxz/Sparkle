package de.moltenKt.paper.structure.app.interchange

import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.completion.emptyInterchangeStructure
import de.moltenKt.paper.structure.command.execution
import de.moltenKt.paper.tool.display.message.Transmission.Level.ERROR

class IssuedInterchange(
	label: String,
	aliases: Set<String>,
) : Interchange(
	label = label,
	aliases = aliases,
	protectedAccess = false,
	completion = emptyInterchangeStructure(),
) {

	override val execution = execution {

		lang("interchange.structure.issue.register")
			.notification(ERROR, executor)
			.display()

		SUCCESS
	}

}