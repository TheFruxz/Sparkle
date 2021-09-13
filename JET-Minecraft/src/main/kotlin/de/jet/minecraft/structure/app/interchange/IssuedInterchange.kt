package de.jet.minecraft.structure.app.interchange

import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.lang
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.emptyCompletion
import de.jet.minecraft.structure.command.live.InterchangeAccess
import de.jet.minecraft.tool.display.message.Transmission.Level.ERROR

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